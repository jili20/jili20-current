package com.jili20.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jili20.entity.Article;
import com.jili20.result.Result;
import com.jili20.vo.admin.FrontAdminArticleQuery;
import com.jili20.vo.api.*;
import com.jili20.vo.user.ArticleVo;
import com.jili20.vo.user.UpdateArticleInfoVo;
import com.jili20.vo.user.UserDraftArticleListVo;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
public interface ArticleService extends IService<Article> {


    /**
     * 用户操作 - 文章管理 - 新增文章 - 或新增草稿
     *
     * @param vo
     * @return
     */
    Result add(ArticleVo vo);


    /**
     * 门户网站 - 首页 - 条件分页查询文章列表
     *
     * @param current
     * @param size
     * @param query
     * @return
     */
    IPage<ArticleIndexListVo> listPage(Long current, Long size, ArticleListQuery query);


    /**
     * 门户网站 -文章详情 - 根据文章id查询文章详情
     *
     * @param articleId
     * @return
     */
    Result getArticleInfoById(Long articleId);


    /**
     * 用户操作 - 前台 - 编辑文章
     *
     * @param vo
     * @return
     */
    Result updateByArticleId(UpdateArticleInfoVo vo);


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
    IPage<ArticleListVo> getUserArticleList(Long routeId, Long userId, Long current, Long size, ApiUserArticleListQuery query);


    /**
     * 后台管理 - 文章管理 - 推荐或取消推荐文章
     *
     * @param articleId
     * @param recommend
     * @return
     */
    Result recommend(Long articleId, String recommend);


    /**
     * 门户网站 - 公共 - 文章推荐列表
     *
     * @return
     */
    List<ArticleRecommendVo> recommendArticleList();


    /**
     * 门户网站 - 文章详情 - 根据ID逻辑删除文章 - 删除文章内容
     * 后台管理 - 文章管理 - 根据文章ID逻辑删除文章 - 删除ES里的文章 - 删除文章内容表内容
     *
     * @param articleId
     * @return
     */
    Result removeByArticleId(Long articleId);


    /**
     * 门户网站 - 私人空间 - 用户动态流
     *
     * @param current
     * @param size
     * @return
     */
    IPage<ArticleListVo> getUserAndFolloweeArticleList( Long current, Long size);


    /**
     * 门户网站 - 私人空间-草稿列表 - 根据用户ID分页查询草稿文章列表
     *
     * @param userId
     * @param current
     * @param size
     * @return
     */
    IPage<UserDraftArticleListVo> getUserDraftArticleList(Long userId, Long current, Long size);


    /**
     * 门户网站 - 编辑文章或草稿 - 根据id查看编辑状态的草稿或文章详情
     *
     * @param articleId
     * @return
     */
    Result viewEditByArticleId(Long articleId);


    /**
     * 门户网站 - 私人空间 - 根据文章id查看已编辑好的草稿详情
     *
     * @param articleId
     * @return
     */
    Result getArticleDraftInfoById(Long articleId);


    /**
     * 远程调用文章微服务 - 用户修改用户名 - 门户网站 - 私人空间
     *
     * @param username
     * @param userId
     * @return
     */
    Boolean updateUsernameByUserId(String username, Long userId);


    /**
     * 远程调用文章微服务 - 用户修改头像 - 门户网站 - 私人空间
     *
     * @param avatar
     * @param userId
     * @return
     */
    Boolean userUpdateAvatar(String avatar, Long userId);


    /**
     * 门户网站 - 使用 ElasticSearch - 搜索文章
     *
     * @param keyword 搜索框输入的 - 关键词
     * @param current 当前页码
     * @param size    每页显示记录数
     * @return
     */
    Result searchEsArticle(String keyword, Integer current, Integer size);


    /**
     * 门户网站 - 获取搜索热词
     *
     * @return
     */
    Result getSearchHotWords();


    /**
     * 门户网站后台管理 - 条件分页查询文章列表
     * @param current
     * @param size
     * @param query
     * @return
     */
    Result queryPage(Long current, Long size, FrontAdminArticleQuery query);

}
