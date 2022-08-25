package com.jili20.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jili20.entity.Article;
import com.jili20.vo.admin.AdminArticleListVo;
import com.jili20.vo.admin.AuditArticleVo;
import com.jili20.vo.api.ArticleIndexListVo;
import com.jili20.vo.api.ArticleListVo;
import com.jili20.vo.api.EsArticleVo;
import com.jili20.vo.user.UserDraftArticleListVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
public interface ArticleMapper extends BaseMapper<Article> {


    /**
     * 门户网站 - 首页 - 条件分页查询文章列表
     *
     * @param pageParam
     * @param wrapper
     * @return
     */

    List<ArticleIndexListVo> indexListPage(@Param("pageParam") Page<ArticleIndexListVo> pageParam,
                                           @Param(Constants.WRAPPER) QueryWrapper<ArticleIndexListVo> wrapper);


    /**
     * 门户网站 - 个人主页 - 根据用户ID条件分页查询文章列表
     * 门户网站 - 私人空间 - 用户动态流（个人与关注人文章列表）
     *
     * @param pageParam
     * @param wrapper
     * @return
     */
    List<ArticleListVo> listPage(@Param("pageParam") Page<ArticleListVo> pageParam,
                                 @Param(Constants.WRAPPER) QueryWrapper<ArticleListVo> wrapper);


    /**
     * 后台管理 - 文章管理 - 条件分页查询文章列表
     *
     * @param pageParam
     * @param wrapper
     * @return
     */
    List<AdminArticleListVo> search(@Param("pageParam") Page<AdminArticleListVo> pageParam, @Param(Constants.WRAPPER) QueryWrapper<AdminArticleListVo> wrapper);


    /**
     * 后台管理 -文章管理 - 根据文章ID查看文章详情
     *
     * @param articleId
     * @return
     */
    AuditArticleVo view(Long articleId);


    /**
     * 后台管理 - 首页 - 统计近6个月发布的文章数
     *
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> selectMonthArticleTotal();


    /**
     * 后台管理 - 首页 - 统计各分类下的文章数 - 显示一级分类名
     *
     * @return
     */
    @MapKey("id")
    List<Map<String, Object>> selectCategoryTotal();


    /**
     * 门户网站 - 文章详情 - 获取一二级分类名
     * @param articleId
     * @return
     */
    //CategoryNameVo getCategoryNameByArticleId(@Param("articleId") Long articleId);


    /**
     * 门户网站 - 私人空间-草稿列表 - 根据用户ID分页查询草稿文章列表
     *
     * @param pageParam
     * @param wrapper
     * @return
     */
    List<UserDraftArticleListVo> listPageDraft(@Param("pageParam") Page<UserDraftArticleListVo> pageParam, @Param(Constants.WRAPPER) QueryWrapper<UserDraftArticleListVo> wrapper);


    /**
     * 把门户网站首页文章列表存入ES - 供搜索
     *
     * @return
     */
    List<EsArticleVo> selectArticleListToEs();

}
