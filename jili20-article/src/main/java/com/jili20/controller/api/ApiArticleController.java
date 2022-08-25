package com.jili20.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.ArticleService;
import com.jili20.vo.api.*;
import com.jili20.vo.user.UserDraftArticleListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bing_  @create 2022/1/20-9:33
 */
@Api(tags = "API - 文章管理")
@RestController
@RequestMapping("/api/article")
public class ApiArticleController {

    @Resource
    private ArticleService articleService;


    @ApiOperation("门户网站 - 首页 - 条件分页查询文章列表")
    @GetMapping("list/{current}/{size}")
    public Result listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long current,
                           @ApiParam(value = "每页记录数", required = true) @PathVariable Long size,
                           @ApiParam("文章列表查询对象") ArticleListQuery query) {
        IPage<ArticleIndexListVo> pageModel = articleService.listPage(current, size, query);
        List<ArticleIndexListVo> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return Result.ok().data("records", records).data("total", total);
    }


    @ApiOperation("门户网站 -文章详情 - 根据文章id查询文章详情")
    @GetMapping("/{articleId}")
    public Result view(@PathVariable("articleId") Long articleId) {
        // 判空
        Assert.notNull(articleId, ResponseEnum.ARTICLE_ID_NULL_ERROR);
        Assert.isTrue(articleId.toString().length() > 0 && articleId.toString().length() < 20, ResponseEnum.REQUEST_ID_LENGTH_ERROR);
        return articleService.getArticleInfoById(articleId);
    }


    @ApiOperation("门户网站 - 公共 - 文章推荐列表")
    @GetMapping("/recommend")
    public Result recommend() {
        List<ArticleRecommendVo> recommendList = articleService.recommendArticleList();
        return Result.ok().data("recommendList", recommendList);
    }


    @ApiOperation("门户网站 - 个人主页-文章列表 - 根据用户ID条件分页查询文章列表")
    @GetMapping("/user/{routeId}/{userId}/{current}/{size}")
    public Result getUserArticleList(@PathVariable Long routeId,
                                     @PathVariable Long userId,
                                     @PathVariable Long current,
                                     @PathVariable Long size,
                                     ApiUserArticleListQuery query) {
        IPage<ArticleListVo> pageModel = articleService.getUserArticleList(routeId, userId, current, size, query);
        List<ArticleListVo> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return Result.ok().data("records", records).data("total", total);
    }


    @ApiOperation("门户网站 - 私人空间 - 用户动态流")
    @GetMapping("/user/{current}/{size}")
    public Result getUserAndFolloweeArticleList(@PathVariable Long current,
                                                @PathVariable Long size) {
        // 使用门户网站-首页文章列表的VO
        IPage<ArticleListVo> pageModel = articleService.getUserAndFolloweeArticleList(current, size);
        List<ArticleListVo> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return Result.ok().data("total", total).data("records", records);
    }


    @ApiOperation("门户网站 - 私人空间-草稿列表 - 根据用户ID分页查询草稿文章列表")
    @GetMapping("/user/draft/{userId}/{current}/{size}")
    public Result getUserDraftArticleList(@ApiParam(value = "用户ID", required = true) @PathVariable Long userId,
                                          @ApiParam(value = "当前页码", required = true) @PathVariable Long current,
                                          @ApiParam(value = "每页记录数", required = true) @PathVariable Long size) {
        if (userId == null) {
            return null;
        }
        IPage<UserDraftArticleListVo> pageModel = articleService.getUserDraftArticleList(userId, current, size);
        List<UserDraftArticleListVo> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return Result.ok().data("records", records).data("total", total);
    }


    @ApiOperation("门户网站 - 使用 ElasticSearch - 搜索文章")
    @GetMapping("/search/{keyword}/{current}/{size}")
    public Result searchEsArticle(@PathVariable("keyword") String keyword,
                                  @PathVariable("current") Integer current,
                                  @PathVariable("size") Integer size) {
        return articleService.searchEsArticle(keyword, current, size);
    }


    @ApiOperation("门户网站 - 获取搜索热词")
    @GetMapping("/hto-words")
    public Result getSearchHotWords() {
        return articleService.getSearchHotWords();
    }

}
