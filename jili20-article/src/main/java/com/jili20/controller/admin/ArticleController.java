package com.jili20.controller.admin;

import com.jili20.enums.ArticleEnum;
import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.ArticleService;
import com.jili20.vo.admin.FrontAdminArticleQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Api(tags = "Admin - 文章管理")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;


    @PreAuthorize("hasAuthority('article:audit')")
    @ApiOperation("后台管理 - 文章管理 - 文章设置为推荐")
    @GetMapping("/recommend/{articleId}")
    public Result recommendYes(@PathVariable("articleId") Long articleId) {
        Assert.notNull(articleId, ResponseEnum.ARTICLE_ID_NULL_ERROR);
        // 推荐（由后端设置值，不需要前端传值）
        String recommend = ArticleEnum.RECOMMEND_YES.getCode();
        return articleService.recommend(articleId, recommend);
    }


    @PreAuthorize("hasAuthority('article:audit')")
    @ApiOperation("后台管理 - 文章管理 - 取消推荐文章")
    @GetMapping("/recommend/cancel/{articleId}")
    public Result recommendNo(@PathVariable("articleId") Long articleId) {
        Assert.notNull(articleId, ResponseEnum.ARTICLE_ID_NULL_ERROR);
        // 取消推荐（由后端设置值，不需要前端传值）
        String recommend = ArticleEnum.RECOMMEND_NO.getCode();
        return articleService.recommend(articleId, recommend);
    }


    @ApiOperation("后台管理 - 文章管理 - 根据文章ID逻辑删除文章 - 删除ES里的文章 - 删除文章内容表内容")
    @PreAuthorize("hasAuthority('article:delete')")
    @DeleteMapping("/admin/{articleId}")
    public Result remove(@PathVariable("articleId") Long articleId) {
        Assert.notNull(articleId, ResponseEnum.ARTICLE_ID_NULL_ERROR);
        // 删除文章内容表数据
        return articleService.removeByArticleId(articleId);
    }



    @ApiOperation("门户网站后台管理 - 条件分页查询文章列表")
    @PreAuthorize("hasAuthority('article:search')")
    @PostMapping("/front/search/{current}/{size}")
    public Result listPageFront(@PathVariable Long current,
                                @PathVariable Long size,
                                @RequestBody FrontAdminArticleQuery query) {
        return articleService.queryPage(current, size, query);
    }


}

