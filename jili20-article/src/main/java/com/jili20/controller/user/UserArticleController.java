package com.jili20.controller.user;

import com.jili20.enums.UserStatusEnum;
import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.ArticleService;
import com.jili20.util.AuthUtil;
import com.jili20.util.RegexValidateUtils;
import com.jili20.vo.user.ArticleVo;
import com.jili20.vo.user.UpdateArticleInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 * @EnableAsync 开启异步任务
 * @Async 给希望异步执行的方法上标注此注解
 * @author Bing
 * @since 2022-01-18
 */
@Api(tags = "User - 文章管理")
@RestController
@RequestMapping("/article")
public class UserArticleController {

    @Resource
    private ArticleService articleService;


    @ApiOperation("门户网站 -  新增文章 - 或新增草稿")
    @PostMapping("/add")
    public Result add(@RequestBody ArticleVo vo) {
        // 检查当前登录用户状态，1 为已被禁言
        String status = AuthUtil.getAuthUserStatus();
        if (UserStatusEnum.FORBIDDEN_SPEAK.getCode().equals(status)) {
            return Result.setResult(ResponseEnum.USER_STATUS_FORBIDDEN_ARTICLE);
        }
        // 取值
        final String masterName = vo.getMasterName();
        final String title = vo.getTitle();
        final Integer categoryId = vo.getCategoryId();
        final Integer categoryPid = vo.getCategoryPid();
        final String masterUrl = vo.getMasterUrl();
        final String content = vo.getContent();
        final String publish = vo.getPublish();
        // 判空（有默认值的字段不用判空，当前登录用户信息不用判空）
        Assert.notEmpty(title, ResponseEnum.ARTICLE_TITLE_NULL_ERROR);
        Assert.notNull(categoryPid, ResponseEnum.ARTICLE_CATEGORY_PID_NULL_ERROR);
        Assert.notNull(categoryId, ResponseEnum.ARTICLE_CATEGORY_ID_NULL_ERROR);
        Assert.notEmpty(content, ResponseEnum.ARTICLE_CONTENT_NULL_ERROR);
        if (StringUtils.isNotBlank(masterUrl) && StringUtils.isBlank(masterName)) {
            return Result.error().message("请填写经历主人姓名");
        }
        if (StringUtils.isNotBlank(masterName) && StringUtils.isBlank(masterUrl)) {
            return Result.error().message("请上传经历主人形象照片");
        }
        // 限制用户输入字段的长度（当前登录用户信息无需校验，直接从 SecurityContextHolder 中获取）
        Assert.isTrue(title.length() >= 1 && title.length() <= 30, ResponseEnum.ARTICLE_TITLE_LENGTH_ERROR);
        //Assert.isTrue(content.length() >= 10 && content.length() <= 20000, ResponseEnum.ARTICLE_CONTENT_LENGTH_ERROR);
        Assert.isTrue(categoryId.toString().length() <= 5, ResponseEnum.ARTICLE_CATEGORY_ID_LENGTH_ERROR);
        Assert.isTrue(categoryPid.toString().length() <= 5, ResponseEnum.ARTICLE_CATEGORY_PID_LENGTH_ERROR);

        if (StringUtils.isNotBlank(masterName)) {
            Assert.isTrue(masterName.length() <= 20, ResponseEnum.ARTICLE_MASTER_NAME_LENGTH_ERROR);
        }
        Assert.isTrue(RegexValidateUtils.checkLength1(publish) ,ResponseEnum.ARTICLE_PUBLISH_LENGTH_ERROR);
        // 执行新增操作
        return articleService.add(vo);
    }


    @ApiOperation("门户网站 - 编辑文章")
    @PutMapping("/update")
    public Result update(@RequestBody UpdateArticleInfoVo vo) {
        // 检查当前登录用户状态，1 为已被禁言
        String status = AuthUtil.getAuthUserStatus();
        if (UserStatusEnum.FORBIDDEN_SPEAK.getCode().equals(status)) {
            return Result.error().message("抱歉，您已被禁言，不能编辑文章");
        }
        // 取值
        final String masterName = vo.getMasterName();
        final String title = vo.getTitle();
        final Integer categoryId = vo.getCategoryId();
        final Integer categoryPid = vo.getCategoryPid();
        final String masterUrl = vo.getMasterUrl();
        final String content = vo.getContent();
        // 判空（有默认值的字段不用判空，当前登录用户信息从 SecurityContextHolder 中获取）
        Assert.notNull(vo.getId(), ResponseEnum.ARTICLE_ID_NULL_ERROR);
        Assert.notEmpty(title, ResponseEnum.ARTICLE_TITLE_NULL_ERROR);
        Assert.notNull(categoryId, ResponseEnum.ARTICLE_CATEGORY_ID_NULL_ERROR);
        Assert.notNull(categoryPid, ResponseEnum.ARTICLE_CATEGORY_PID_NULL_ERROR);
        Assert.notEmpty(content, ResponseEnum.ARTICLE_CONTENT_NULL_ERROR);
        if (StringUtils.isNotBlank(masterUrl) && StringUtils.isBlank(masterName)) {
            return Result.error().message("请填写经历主人姓名");
        }
        if (StringUtils.isNotBlank(masterName) && StringUtils.isBlank(masterUrl)) {
            return Result.error().message("请上传经历主人形象照片");
        }
        // 限制用户输入字段的长度（当前登录用户信息无需校验，直接从 SecurityContextHolder 中获取）
        Assert.isTrue(title.length() >= 1 && title.length() <= 30, ResponseEnum.ARTICLE_TITLE_LENGTH_ERROR);
        //Assert.isTrue(content.length() >= 10 && content.length() <= 20000, ResponseEnum.ARTICLE_CONTENT_LENGTH_ERROR);
        Assert.isTrue(categoryId.toString().length() <= 5, ResponseEnum.ARTICLE_CATEGORY_ID_LENGTH_ERROR);
        Assert.isTrue(categoryPid.toString().length() <= 5, ResponseEnum.ARTICLE_CATEGORY_PID_LENGTH_ERROR);
        if (StringUtils.isNotBlank(masterName)) {
            Assert.isTrue(masterName.length() <= 20, ResponseEnum.ARTICLE_MASTER_NAME_LENGTH_ERROR);
        }
        // 调用方法执行更新
        return articleService.updateByArticleId(vo);
    }


    @ApiOperation("门户网站 - 文章详情 - 根据ID逻辑删除文章 - 删除文章内容")
    @DeleteMapping("/remove/{articleId}")
    public Result remove(@PathVariable("articleId") Long articleId) {
        Assert.notNull(articleId, ResponseEnum.ARTICLE_ID_NULL_ERROR);
        return articleService.removeByArticleId(articleId);
    }


    
    @ApiOperation("门户网站 - 根据id查看编辑状态的草稿或文章详情 - 编辑文章、草稿")
    @GetMapping("/user/edit/{articleId}")
    public Result viewEditByArticleInfo(@PathVariable("articleId") Long articleId) {
        // 判空
        Assert.notNull(articleId, ResponseEnum.ARTICLE_ID_NULL_ERROR);
        return articleService.viewEditByArticleId(articleId);
    }



    @ApiOperation("门户网站 - 私人空间 - 根据文章id查看已编辑好的草稿详情")
    @GetMapping("/draft/{articleId}")
    public Result viewDraft(@PathVariable("articleId") Long articleId) {
        // 判空
        Assert.notNull(articleId, ResponseEnum.ARTICLE_ID_NULL_ERROR);
        return articleService.getArticleDraftInfoById(articleId);
    }



}

