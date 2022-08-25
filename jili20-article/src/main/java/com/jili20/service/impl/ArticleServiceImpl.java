package com.jili20.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jili20.aliyun.AliyunUtil;
import com.jili20.aliyun.Properties;
import com.jili20.entity.Article;
import com.jili20.entity.ArticleContent;
import com.jili20.entity.Category;
import com.jili20.entity.Looper;
import com.jili20.enums.ArticleEnum;
import com.jili20.enums.UserStatusEnum;
import com.jili20.exception.Assert;
import com.jili20.mapper.ArticleMapper;
import com.jili20.mapper.CategoryMapper;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.ArticleContentService;
import com.jili20.service.ArticleService;
import com.jili20.service.LooperService;
import com.jili20.service.ThreadService;
import com.jili20.util.AuthUtil;
import com.jili20.vo.admin.FrontAdminArticleQuery;
import com.jili20.vo.admin.FrontAdminArticleVo;
import com.jili20.vo.api.*;
import com.jili20.vo.user.ArticleVo;
import com.jili20.vo.user.UpdateArticleInfoVo;
import com.jili20.vo.user.UserDraftArticleListVo;
import com.jili20.vo.user.UserEditArticleInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    public static final String ES_INDEX_ARTICLE = "article";

    public static final String ARTICLE_SEARCH_HOT_WORDS = "search_hot_words";

    @Resource
    private ArticleContentService articleContentService;

    @Resource
    private ThreadService threadService;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private Properties properties;

    @Resource
    private LooperService looperService;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 门户网站 - 文章管理 - 新增文章 - 或新增草稿
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Result add(ArticleVo vo) {
        // 检查当前登录用户状态，1 为已被禁言
        String status = AuthUtil.getAuthUserStatus();
        if (UserStatusEnum.FORBIDDEN_SPEAK.getCode().equals(status)) {
            return Result.error().message("您已被禁言，不能发布文章");
        }
        // 如果是发布文章，检查标题是否重复。如果发布的是草稿，无需检查。
        if (vo.getPublish().equals(ArticleEnum.PUBLISH_YES.getCode())) {
            // 校验文章标题是否已存在
            Integer count = baseMapper.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getTitle, vo.getTitle()));
            if (count > 0) {
                return Result.error().message("文章标题已存在");
            }
        }
        // 新增文章
        Article article = new Article();
        // 设置作者信息（当前登录用户）
        article.setUserId(AuthUtil.getAuthUserId());
        article.setUsername(AuthUtil.getAuthUsername());
        article.setAvatar(AuthUtil.getAuthUserAvatar());
        // 设置文章信息
        article.setTitle(vo.getTitle());
        article.setCategoryId(vo.getCategoryId());
        article.setCategoryPid(vo.getCategoryPid());
        article.setMasterName(vo.getMasterName());
        article.setMasterUrl(vo.getMasterUrl());
        article.setPublish(vo.getPublish());
        // 要设置时间不能只用数据库默认的值，否则ES获取不到时间
        article.setCreateTime(LocalDateTime.now());
        // 保存到数据库
        baseMapper.insert(article);
        // 设置文章内容
        ArticleContent articleContent = new ArticleContent();
        // 文章内容表的ID与文章表ID一致
        articleContent.setId(article.getId());
        articleContent.setContent(vo.getContent());
        // 保存文章内容到文章内容表
        articleContentService.save(articleContent);
        // 将新增文章保存到 ES EsArticleVo 中
        // 只保存 已发布审核通过的帖子
        if (vo.getPublish().equals(ArticleEnum.PUBLISH_YES.getCode())) {
            try {
                final EsArticleVo es = new EsArticleVo();
                es.setId(article.getId());
                es.setUserId(AuthUtil.getAuthUserId());
                es.setMasterUrl(article.getMasterUrl());
                es.setTitle(article.getTitle());
                es.setCategoryId(article.getCategoryId());
                es.setCategoryPid(article.getCategoryPid());
                es.setCreateTime(article.getCreateTime());
                // 根据分类ID，查询分类表分类名和父分类名，设置到ES中（这里用程序查）
                final Category c1 = categoryMapper.selectById(article.getCategoryId());
                final Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                        .eq(Category::getId, c1.getParentId())
                        .select(Category::getCategoryName));
                // 将分类名和父分类名设置到ES对象
                es.setCategoryName(c1.getCategoryName());
                es.setCategoryParentName(category.getCategoryName());
                IndexRequest request = new IndexRequest(ES_INDEX_ARTICLE);
                request.id(article.getId() + "");
                request.source(objectMapper.writeValueAsString(es), XContentType.JSON);
                restHighLevelClient.index(request, RequestOptions.DEFAULT);

            } catch (IOException e) {
                log.error(" ArticleServiceImpl 新增文章保存到 ElasticSearch 错误 :{}", e);
            }
        }
        // 返回文章ID
        return Result.ok().data("articleId", article.getId()).message("新增文章成功");
    }


    /**
     * 门户网站 - 首页 - 条件分页查询文章列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public IPage<ArticleIndexListVo> listPage(Long current, Long size, ArticleListQuery query) {
        // 组装查询条件：指定用户ID、已发布、审核通过的文章
        QueryWrapper<ArticleIndexListVo> wrapper = new QueryWrapper<>();
        wrapper.eq("a.publish", ArticleEnum.PUBLISH_YES.getCode());
        wrapper.eq("a.is_deleted", false);
        // 条件查询
        wrapper.eq(Objects.nonNull(query.getCategoryId()) && query.getCategoryId() > 0, "c1.id", query.getCategoryId());
        wrapper.eq(Objects.nonNull(query.getCategoryPid()) && query.getCategoryPid() > 0, "c2.id", query.getCategoryPid());
        wrapper.orderByDesc(Objects.nonNull(query.getViewCount()) && query.getViewCount() > 0, "view_count");
        wrapper.orderByDesc(Objects.nonNull(query.getCreateTime()) && query.getCreateTime().length() > 0, "a.create_time");
        wrapper.orderByAsc(Objects.nonNull(query.getUpdateTime()) && query.getUpdateTime().length() > 0, "a.create_time");
        // 组装分页
        Page<ArticleIndexListVo> pageParam = new Page<>(current, size);
        // 执行分页查询(只需要在 mapper 层传入封装好的分页组件即可，sql 分页条件组装的过程由mp自动完成)
        List<ArticleIndexListVo> records = baseMapper.indexListPage(pageParam, wrapper);
        // 将 records 设置到 pageParam 中
        return pageParam.setRecords(records);
    }


    /**
     * 门户网站 -文章详情 - 根据文章id查询文章详情
     *
     * @param articleId
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Result getArticleInfoById(Long articleId) {
        // 查出文章
        Article article = baseMapper.selectById(articleId);
        Assert.notNull(article, ResponseEnum.ARTICLE_NULL_ERROR);
        // 使用多线程更新文章浏览数
        threadService.updateViewCount(articleMapper, articleId);
        // 将查出来的文章信息，复制到 ArticleInfoVo
        final ArticleInfoVo vo = new ArticleInfoVo();
        vo.setId(article.getId());
        vo.setUserId(article.getUserId());
        vo.setUsername(article.getUsername());
        vo.setAvatar(article.getAvatar());
        vo.setTitle(article.getTitle());
        vo.setCategoryId(article.getCategoryId());
        vo.setCategoryPid(article.getCategoryPid());
        vo.setMasterName(article.getMasterName());
        vo.setMasterUrl(article.getMasterUrl());
        vo.setViewCount(article.getViewCount());
        vo.setCreateTime(article.getCreateTime());
        vo.setUpdateTime(article.getUpdateTime());
        // 根据文章ID查出文章内容，封装到 ArticleInfoVo
        final ArticleContent articleContent = articleContentService.getById(articleId);
        if (articleContent != null) {
            vo.setContent(articleContent.getContent());
        }
        // 根据分类ID，查询分类表分类名和父分类名（这里用程序查）
        final Category c1 = categoryMapper.selectById(article.getCategoryId());
        final Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .eq(Category::getId, c1.getParentId())
                .select(Category::getCategoryName));
        // 将查出来的分类名和父分类设置到 ArticleInfoVo
        vo.setCategoryName(c1.getCategoryName());
        vo.setCategoryParentName(category.getCategoryName());

        return Result.ok().data("articleInfo", vo);
    }


    /**
     * 用户操作 - 前台 - 编辑文章
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Result updateByArticleId(UpdateArticleInfoVo vo) {
        // 校验通过，执行更新
        Article article = baseMapper.selectById(vo.getId());
        Assert.notNull(article, ResponseEnum.ARTICLE_NULL_ERROR);
        // 如果文章为已发布，不能再保存为草稿
        if (article.getPublish().equals(ArticleEnum.PUBLISH_YES.getCode()) && vo.getPublish().equals(ArticleEnum.PUBLISH_NO.getCode())) {
            return Result.error().message("已发布文章不能再保存为草稿");
        }
        // 检查更新的文章标题是否已存在
        if (!StringUtils.equals(article.getTitle(), vo.getTitle())) {
            Article title = baseMapper.selectOne(new QueryWrapper<Article>().eq("title", vo.getTitle()));
            Assert.isNull(title, ResponseEnum.ARTICLE_TITLE_EXIST_ERROR);
        }
        // 更新用户信息
        article.setUserId(AuthUtil.getAuthUserId());
        article.setUsername(AuthUtil.getAuthUsername());
        article.setAvatar(AuthUtil.getAuthUserAvatar());
        // 更新其它信息
        article.setMasterName(vo.getMasterName());
        article.setMasterUrl(vo.getMasterUrl());
        article.setCategoryId(vo.getCategoryId());
        article.setCategoryPid(vo.getCategoryPid());
        article.setPublish(vo.getPublish());
        article.setTitle(vo.getTitle());
        article.setUpdateTime(LocalDateTime.now());
        // 插入数据库
        baseMapper.updateById(article);
        // 更新文章内容表数据
        ArticleContent articleContent = articleContentService.getById(article.getId());
        articleContent.setContent(vo.getContent());
        articleContent.setUpdateTime(LocalDateTime.now());
        articleContentService.updateById(articleContent);
        // 修改 ES 中的文章信息（ES 中有ID则修改，没有ID则新增。所以还是用新增的方法）
        if (vo.getPublish().equals(ArticleEnum.PUBLISH_YES.getCode())) {
            try {
                final EsArticleVo es = new EsArticleVo();
                es.setId(article.getId());
                es.setUserId(AuthUtil.getAuthUserId());
                es.setMasterUrl(vo.getMasterUrl());
                es.setTitle(article.getTitle());
                es.setCategoryId(vo.getCategoryId());
                es.setCategoryPid(vo.getCategoryPid());
                es.setCreateTime(article.getUpdateTime());
                // 根据分类ID，查询分类表分类名和父分类名（这里用程序查）
                final Category c1 = categoryMapper.selectById(article.getCategoryId());
                final Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                        .eq(Category::getId, c1.getParentId())
                        .select(Category::getCategoryName));
                // 将查出来的分类名设置到ES对象中
                es.setCategoryName(c1.getCategoryName());
                es.setCategoryParentName(category.getCategoryName());
                IndexRequest request = new IndexRequest(ES_INDEX_ARTICLE);
                request.id(article.getId() + "");
                request.source(objectMapper.writeValueAsString(es), XContentType.JSON);
                restHighLevelClient.index(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                log.error(" ArticleServiceImpl 新增文章保存到 ElasticSearch 错误 :{}", e);
            }
        }

        return Result.ok().data("articleId", article.getId().toString()).message("编辑文章成功");
    }


    /**
     * 门户网站 - 用户文章列表 - 根据用户ID条件分页查询文章列表
     *
     * @param routeId 路由ID（要查询数据对应的用户ID）
     * @param userId  当前用户ID（是否已点击当前文章用）
     * @param current
     * @param size
     * @param query
     * @return
     */
    @Override
    public IPage<ArticleListVo> getUserArticleList(Long routeId, Long userId, Long current, Long size, ApiUserArticleListQuery query) {
        // 组装查询条件：指定用户ID、已发布、审核通过的文章
        QueryWrapper<ArticleListVo> wrapper = new QueryWrapper<>();
        wrapper.eq("a.user_id", routeId);
        wrapper.eq("a.is_deleted", false);
        wrapper.eq("publish", ArticleEnum.PUBLISH_YES.getCode());
        // 条件查询
        wrapper.orderByDesc(Objects.nonNull(query.getViewCount()) && query.getViewCount() > 0, "view_count");
        wrapper.orderByDesc(Objects.nonNull(query.getCreateTime()) && query.getCreateTime().length() > 0, "a.create_time");
        // 组装分页
        Page<ArticleListVo> pageParam = new Page<>(current, size);
        // 执行分页查询(只需要在 mapper 层传入封装好的分页组件即可，sql 分页条件组装的过程由mp自动完成)
        // 这里调用了门户网站首页查询文章列表的方法
        List<ArticleListVo> records = baseMapper.listPage(pageParam, wrapper);
        // 将 records 设置到 pageParam 中
        return pageParam.setRecords(records);
    }


    /**
     * 门户网站 - 文章详情 - 根据ID逻辑删除文章 - 删除文章内容 - 删除阿里云OSS中文章图片
     * 后台管理 - 文章管理 - 根据文章ID逻辑删除文章 - 删除ES里的文章 - 删除文章内容表内容
     *
     * @param articleId
     * @return
     */
    @Override
    public Result removeByArticleId(Long articleId) {
        // 查出来，先删除文章内容表内容，再删除经历主人照片，后删除文章表内容，否则无法删除文章内容表内容
        final Article article = baseMapper.selectById(articleId);
        if (article == null) {
            return Result.error().message("该文章不存在");
        }
        // 1、如果有经历主人头像，删除
        if (StringUtils.isNotBlank(article.getMasterUrl())) {
            AliyunUtil.delete(article.getMasterUrl(), properties.getAliyun());
        }
        // 2、删除ES中的数据
        try {
            // 根据ID删除 ES 中的文章
            DeleteRequest deleteRequest = new DeleteRequest("article", articleId.toString());
            // 发起请求
            restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(" ArticleController 删除 ElasticSearch 文章错误 :{}", e);
            e.printStackTrace();
        }
        // 3、使用多线程删除阿里云OSS中的文章内容图片
        final String content = articleContentService.getById(article.getId()).getContent();
        threadService.deletePictureList(content, articleId);
        // 5、删除文章内容表数据
        final boolean count = articleContentService.removeById(article.getId());
        if (count) {
            final int result = baseMapper.deleteById(articleId);
            if (result > 0) {
                return Result.ok().message("删除文章成功");
            }
            return Result.error().message("删除文章失败");
        }
        return Result.ok().message("删除文章成功");
    }


    /**
     * 后台管理 - 文章管理 - 文章设置为推荐 或取消推荐
     *
     * @param articleId
     * @param recommend
     * @return 删除缓存
     */
    @CacheEvict(value = "article", allEntries = true)
    @Override
    public Result recommend(Long articleId, String recommend) {
        // 查询数据库文章
        Article article = baseMapper.selectById(articleId);
        Assert.notNull(article, ResponseEnum.ARTICLE_NULL_ERROR);
        // 修改文章状态和更新时间
        article.setRecommend(recommend);
        article.setUpdateTime(LocalDateTime.now());
        // 插入数据库
        baseMapper.updateById(article);
        // 推荐文章
        if (recommend.equals(ArticleEnum.RECOMMEND_YES.getCode())) {
            return Result.ok().message("文章推荐成功");
        }
        // 取消推荐
        if (recommend.equals(ArticleEnum.RECOMMEND_NO.getCode())) {
            return Result.ok().message("已取消推荐");
        }
        return Result.error().message("文章推荐失败");
    }


    /**
     * 门户网站 - 公共 - 文章推荐列表
     *
     * @return
     */
    @Cacheable(value = {"article"}, key = "#root.methodName", sync = true)
    @Override
    public List<ArticleRecommendVo> recommendArticleList() {
        final List<Article> articles = baseMapper.selectList(new QueryWrapper<Article>()
                .eq("recommend", ArticleEnum.RECOMMEND_YES.getCode())
                .select("id", "title")
                .orderByDesc("update_time")
                .last("limit 6"));
        // 遍历封装为VO类型，只取前端显示的数据
        if (articles != null) {
            List<ArticleRecommendVo> recommendVoList = new ArrayList<>();
            for (Article article : articles) {
                final ArticleRecommendVo vo = new ArticleRecommendVo();
                vo.setId(article.getId());
                vo.setTitle(article.getTitle());
                recommendVoList.add(vo);
            }
            return recommendVoList;
        }
        return null;
    }


    /**
     * 门户网站 - 私人空间 - 用户动态流（个人与关注人文章列表）
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public IPage<ArticleListVo> getUserAndFolloweeArticleList(Long current, Long size) {
        // 查询条件
        QueryWrapper<ArticleListVo> wrapper = new QueryWrapper<>();
        wrapper.eq("publish", ArticleEnum.PUBLISH_YES.getCode())
                .eq("a.is_deleted", false)
                .orderByDesc("a.create_time");
        // 组装分页
        Page<ArticleListVo> pageParam = new Page<>(current, size);
        // 执行分页查询
        // 只需要在 mapper 层传入封装好的分页组件即可，sql 分页条件组装的过程由mp自动完成
        List<ArticleListVo> records = baseMapper.listPage(pageParam, wrapper);
        // 将 records 设置到 pageParam 中
        return pageParam.setRecords(records);
    }


    /**
     * 门户网站  - 私人空间-草稿列表 - 根据用户ID分页查询草稿文章列表
     *
     * @param
     * @param size
     * @param current
     * @return
     */
    @Override
    public IPage<UserDraftArticleListVo> getUserDraftArticleList(Long userId, Long current, Long size) {
        // 查询条件（没有规定状态，因审核不通过的文章也要查询出来）
        QueryWrapper<UserDraftArticleListVo> wrapper = new QueryWrapper<>();
        wrapper.eq("a.user_id", userId)
                .eq("publish", ArticleEnum.PUBLISH_NO.getCode())
                .eq("a.is_deleted", false)
                .orderByDesc("a.create_time");
        // 组装分页
        Page<UserDraftArticleListVo> pageParam = new Page<>(current, size);
        // 执行分页查询
        // 只需要在 mapper 层传入封装好的分页组件即可，sql 分页条件组装的过程由mp自动完成
        List<UserDraftArticleListVo> records = baseMapper.listPageDraft(pageParam, wrapper);

        // 将 records 设置到 pageParam 中
        return pageParam.setRecords(records);
    }


    /**
     * 门户网站 - 编辑文章或草稿 - 根据id查看编辑状态的草稿或文章详情
     *
     * @param articleId
     * @return
     */
    @Override
    public Result viewEditByArticleId(Long articleId) {
        // 查出文章
        Article article = baseMapper.selectById(articleId);
        Assert.notNull(article, ResponseEnum.ARTICLE_NULL_ERROR);

        // 将查出来的文章信息，复制到 UserEditArticleInfoVo
        final UserEditArticleInfoVo vo = new UserEditArticleInfoVo();
        vo.setId(article.getId());
        vo.setUserId(article.getUserId());
        vo.setUsername(article.getUsername());
        vo.setTitle(article.getTitle());
        vo.setCategoryId(article.getCategoryId());
        vo.setCategoryPid(article.getCategoryPid());
        vo.setMasterName(article.getMasterName());
        vo.setMasterUrl(article.getMasterUrl());
        vo.setCreateTime(article.getCreateTime());
        vo.setUpdateTime(article.getUpdateTime());

        // 根据文章ID查出文章内容，封装到 ArticleInfoVo
        final ArticleContent articleContent = articleContentService.getById(articleId);
        if (articleContent != null) {
            vo.setContent(articleContent.getContent());
        }
        return Result.ok().data("articleInfo", vo);
    }


    /**
     * 门户网站 - 私人空间 - 根据文章id查看已编辑好的草稿详情
     *
     * @param articleId
     * @return
     */
    @Override
    public Result getArticleDraftInfoById(Long articleId) {
        // 查出文章
        Article article = baseMapper.selectById(articleId);
        Assert.notNull(article, ResponseEnum.ARTICLE_NULL_ERROR);

        // 将查出来的文章信息，复制到 ArticleInfoVo
        final ArticleInfoVo vo = new ArticleInfoVo();

        vo.setId(article.getId());
        vo.setUserId(article.getUserId());
        vo.setUsername(article.getUsername());
        vo.setAvatar(article.getAvatar());
        vo.setTitle(article.getTitle());
        vo.setCategoryId(article.getCategoryId());
        vo.setCategoryPid(article.getCategoryPid());
        vo.setMasterName(article.getMasterName());
        vo.setMasterUrl(article.getMasterUrl());
        vo.setViewCount(article.getViewCount());
        vo.setCreateTime(article.getCreateTime());
        vo.setUpdateTime(article.getUpdateTime());

        // 根据文章ID查出文章内容，封装到 ArticleInfoVo
        final ArticleContent articleContent = articleContentService.getById(articleId);
        if (articleContent != null) {
            vo.setContent(articleContent.getContent());
        }

        // 根据分类ID，查询分类表分类名和父分类名（这里用程序查）
        final Category c1 = categoryMapper.selectById(article.getCategoryId());
        final Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .eq(Category::getId, c1.getParentId())
                .select(Category::getCategoryName));

        vo.setCategoryName(c1.getCategoryName());
        vo.setCategoryParentName(category.getCategoryName());

        return Result.ok().data("articleInfo", vo);
    }


    /**
     * 远程调用文章微服务 - 用户修改用户名 - 门户网站 - 私人空间
     *
     * @param username
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Boolean updateUsernameByUserId(String username, Long userId) {
        // 根据用户ID查出所有相关的文章ID和用户名
        final List<Article> articles = baseMapper.selectList(new LambdaQueryWrapper<Article>()
                .eq(Article::getUserId, userId));
        // 集合为空返回
        if (articles == null) {
            return true;
        }
        // 批量修改ES中文章的用户名（ES 中有ID则修改，没有ID则新增。所以还是用新增的方法）
        for (Article article : articles) {
            try {
                final EsArticleVo es = new EsArticleVo();
                es.setId(article.getId());
                es.setUserId(article.getUserId());
                es.setMasterUrl(article.getMasterUrl());
                es.setTitle(article.getTitle());
                es.setCategoryId(article.getCategoryId());
                es.setCategoryPid(article.getCategoryPid());
                es.setCreateTime(article.getCreateTime());
                // 根据分类ID，查询分类表分类名和父分类名（这里用程序查）
                final Category c1 = categoryMapper.selectById(article.getCategoryId());
                final Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                        .eq(Category::getId, c1.getParentId())
                        .select(Category::getCategoryName));
                // 将查出来的分类名设置到ES对象中
                es.setCategoryName(c1.getCategoryName());
                es.setCategoryParentName(category.getCategoryName());
                IndexRequest request = new IndexRequest(ES_INDEX_ARTICLE);
                request.id(article.getId() + "");
                request.source(objectMapper.writeValueAsString(es), XContentType.JSON);
                restHighLevelClient.index(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                log.error(" ArticleServiceImpl 更新 ElasticSearch 中的用户头像错误 :{}", e);
            }
        }
        // 更新文章表用户名：遍历每一条数据，设置新的用户名
        articles.forEach(article -> article.setUsername(username));
        // 3、更新轮播图表用户名：遍历每一条数据，设置新的用户名
        final List<Looper> looperList = looperService.list(new LambdaQueryWrapper<Looper>()
                .eq(Looper::getUserId, userId).select(Looper::getId, Looper::getUsername));
        if (looperList == null) {
            return true;
        }
        looperList.forEach(looper -> looper.setUsername(username));
        looperService.updateBatchById(looperList);

        // 3、执行批量更新文章表用户名
        return this.updateBatchById(articles);
    }


    /**
     * 远程调用文章微服务 - 用户修改头像 - 门户网站 - 私人空间
     *
     * @param avatar
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean userUpdateAvatar(String avatar, Long userId) {
        // 根据用户ID查出所有相关的ID和用户头像
        final List<Article> articles = baseMapper.selectList(new LambdaQueryWrapper<Article>()
                .eq(Article::getUserId, userId));
        // 集合为空返回
        if (articles == null) {
            return true;
        }
        // 更新文章表用户头像：遍历每一条数据，设置新的用户头像
        articles.forEach(article -> article.setAvatar(avatar));
        // 批量修改ES中文章的用户名（ES 中有ID则修改，没有ID则新增。所以还是用新增的方法）
        for (Article article : articles) {
            try {
                final EsArticleVo es = new EsArticleVo();
                es.setId(article.getId());
                es.setUserId(article.getUserId());
                es.setMasterUrl(article.getMasterUrl());
                es.setTitle(article.getTitle());
                es.setCategoryId(article.getCategoryId());
                es.setCategoryPid(article.getCategoryPid());
                es.setCreateTime(article.getCreateTime());
                // 根据分类ID，查询分类表分类名和父分类名（这里用程序查）
                final Category c1 = categoryMapper.selectById(article.getCategoryId());
                final Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                        .eq(Category::getId, c1.getParentId())
                        .select(Category::getCategoryName));
                // 将查出来的分类名设置到ES对象中
                es.setCategoryName(c1.getCategoryName());
                es.setCategoryParentName(category.getCategoryName());
                IndexRequest request = new IndexRequest(ES_INDEX_ARTICLE);
                request.id(article.getId() + "");
                request.source(objectMapper.writeValueAsString(es), XContentType.JSON);
                restHighLevelClient.index(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                log.error(" ArticleServiceImpl 更新 ElasticSearch 中的用户头像错误 :{}", e);
            }
        }
        // 3、执行批量更新文章表用户头像
        return this.updateBatchById(articles);
    }


    /**
     * 门户网站 - 使用 ElasticSearch - 搜索文章
     *
     * @param keyword 搜索框输入的 - 关键词
     * @param current 当前页码
     * @param size    每页显示记录数
     * @return
     */
    @Override
    public Result searchEsArticle(String keyword, Integer current, Integer size) {

        // 将搜索关键词存入redis为热搜词，没有过滤敏感词
        double count = 1.0;
        stringRedisTemplate.opsForZSet().incrementScore(ARTICLE_SEARCH_HOT_WORDS, keyword, count);

        try {
            // 用来封装检索条件
            // 从 article 索引中取出数据
            SearchRequest searchRequest = new SearchRequest(ES_INDEX_ARTICLE);
            // 查询条件统一设置到 searchSourceBuilder 中
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            // 查询关键词
            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "title", "categoryParentName", "categoryName"));
            // 分页
            searchSourceBuilder.from((current - 1) * size);
            searchSourceBuilder.size(size);
            // 设置高亮显示的字段
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            HighlightBuilder.Field field2 = new HighlightBuilder.Field("title");
            HighlightBuilder.Field field3 = new HighlightBuilder.Field("categoryParentName");
            HighlightBuilder.Field field4 = new HighlightBuilder.Field("categoryName");
            highlightBuilder.field(field2);
            highlightBuilder.field(field3);
            highlightBuilder.field(field4);
            // 设置高亮标签和颜色
            highlightBuilder.preTags("<b style='color:#ab1b4a'>");
            highlightBuilder.postTags("</b>");
            searchSourceBuilder.highlighter(highlightBuilder);
            searchRequest.source(searchSourceBuilder);
            // 执行检索
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            // 封装查询结果
            SearchHits hits = searchResponse.getHits();
            // 总记录数
            long total = hits.getTotalHits().value;
            Iterator<SearchHit> iterator = hits.iterator();
            List<EsArticleVo> articleList = new ArrayList<>();
            while (iterator.hasNext()) {
                SearchHit searchHit = iterator.next();
                EsArticleVo articleEsVo = objectMapper.readValue(searchHit.getSourceAsString(), EsArticleVo.class);
                // 获取高亮字段
                Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                // 高亮的字段
                HighlightField title = highlightFields.get("title");
                HighlightField categoryParentName = highlightFields.get("categoryParentName");
                HighlightField categoryName = highlightFields.get("categoryName");
                if (title != null) {
                    String highLight2 = Arrays.toString(title.getFragments());
                    // 去掉 ES 高亮字符串的 [ ]
                    articleEsVo.setTitle(highLight2.substring(1, highLight2.indexOf("]")));
                }
                if (categoryParentName != null) {
                    String highLight3 = Arrays.toString(categoryParentName.getFragments());
                    // 去掉 ES 高亮字符串的 [ ]
                    articleEsVo.setCategoryParentName(highLight3.substring(1, highLight3.indexOf("]")));
                }
                if (categoryName != null) {
                    String highLight4 = Arrays.toString(categoryName.getFragments());
                    // 去掉 ES 高亮字符串的 [ ]
                    articleEsVo.setCategoryName(highLight4.substring(1, highLight4.indexOf("]")));
                }
                articleList.add(articleEsVo);
            }
            return Result.ok().data("total", total).data("articles", articleList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 门户网站 - 获取搜索热词
     * 1、redis 的 zset 数据库结构
     * 2、要按照 score 从大到小排序 ARTICLE_SEARCH_HOT_WORDS
     * 3、redis 数据来源，先用测试数据，后期做搜索的时候，redis 数据 通过搜索接口放入
     *
     * @return
     */
    @Override
    public Result getSearchHotWords() {
        // 分数从大到小排列，取前10条数据
        final Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.opsForZSet().reverseRangeWithScores(ARTICLE_SEARCH_HOT_WORDS, 0, 9);
        // 将结果封装为 List<String>
        List<String> hotWords = new ArrayList<>();
        if (typedTuples == null) {
            return Result.ok().data("hotWords", hotWords);
        }
        // 遍历赋值给 list 集合
        for (ZSetOperations.TypedTuple<String> typedTuple : typedTuples) {
            hotWords.add(typedTuple.getValue());
        }
        return Result.ok().data("hotWords", hotWords);
    }


    /**
     * 门户网站后台管理 - 条件分页查询文章列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    @Override
    public Result queryPage(Long current, Long size, FrontAdminArticleQuery query) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(query.getId()) && query.getId() > 0, Article::getId, query.getId());
        wrapper.like(Objects.nonNull(query.getUsername()), Article::getUsername, query.getUsername());
        wrapper.like(Objects.nonNull(query.getMasterName()), Article::getMasterName, query.getMasterName());
        wrapper.like(Objects.nonNull(query.getTitle()), Article::getTitle, query.getTitle());
        wrapper.eq(Objects.nonNull(query.getRecommend()), Article::getRecommend, query.getRecommend());
        wrapper.orderByDesc(Article::getRecommend);
        wrapper.orderByDesc(Article::getUpdateTime);
        // 组装分页
        Page<Article> pageParam = new Page<>(current, size);
        final Page<Article> articlePage = baseMapper.selectPage(pageParam, wrapper);
        final List<Article> records = articlePage.getRecords();
        final long total = articlePage.getTotal();
        // 封装为 VO 类型
        List<FrontAdminArticleVo> listVo = new ArrayList<>();
        for (Article rec : records) {
            final FrontAdminArticleVo vo = new FrontAdminArticleVo();
            vo.setId(rec.getId());
            vo.setUsername(rec.getUsername());
            vo.setMasterName(rec.getMasterName());
            vo.setTitle(rec.getTitle());
            vo.setRecommend(rec.getRecommend());
            vo.setCreateTime(rec.getCreateTime());
            listVo.add(vo);
        }
        return Result.ok().data("records", listVo).data("total", total);
    }


}


