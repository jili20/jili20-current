package com.jili20.controller.api;

import com.jili20.result.Result;
import com.jili20.service.LooperService;
import com.jili20.vo.api.LooperQuery;
import com.jili20.vo.api.LooperTop15Vo;
import com.jili20.vo.api.LooperVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 轮播图表 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Api(tags = "API - 轮播图管理")
@RestController
@RequestMapping("/api/looper")
public class ApiLooperController {

    @Resource
    private LooperService looperService;


    /**
     * 一个方法只能缓存一个位置的轮播图，所有这里每个位置写一个方法
     */
    @ApiOperation("门户网站 - 轮播图 - 审核通过的 - 首页顶部")
    @GetMapping("/top")
    public Result top() {
        List<LooperVo> looperList = looperService.looperList();
        return Result.ok().data("looperListTop", looperList);
    }


    /**
     * 一个方法只能缓存一个位置的轮播图，所有这里每个位置写一个方法
     */
    @ApiOperation("门户网站 - 轮播图 - 审核通过的 - 首页右则")
    @GetMapping("/right")
    public Result right() {
        List<LooperVo> looperList = looperService.looperListIndexRight();
        return Result.ok().data("looperListRight", looperList);
    }

    @ApiOperation("门户网站 - 轮播图 - 审核通过的 - 赞助详情右则")
    @GetMapping("/right/patron")
    public Result rightPatron() {
        List<LooperVo> looperList = looperService.looperListPatronRight();
        return Result.ok().data("looperListRight", looperList);
    }


    /**
     * 一个方法只能缓存一个位置的轮播图，所有这里每个位置写一个方法
     */
    @ApiOperation("门户网站 - 轮播图 - 审核通过的 - 文章详情下")
    @GetMapping("/article")
    public Result article() {
        List<LooperVo> looperList = looperService.looperListArticle();
        return Result.ok().data("looperListArticle", looperList);
    }


    @ApiOperation("门户网站 - 新增轮播图页面 - 获取前15条轮播图信息")
    @GetMapping("/top15")
    public Result top15() {
        List<LooperTop15Vo> looperTop15List = looperService.looperTop15List();
        return Result.ok().data("looperTop15List", looperTop15List);
    }


    @ApiOperation("门户网站 - 条件分页查询 - 已审核通过的所有轮播图列表")
    @GetMapping("/list/{current}/{size}")
    public Result search(@PathVariable Long current, @PathVariable Long size, LooperQuery query) {
        return looperService.search(current, size, query);
    }


    @ApiOperation("门户网站 - 个人主页 - 用户投图赞助列表")
    @GetMapping("/list/{userId}/{current}/{size}")
    public Result search(@PathVariable Long userId,
                         @PathVariable Long current,
                         @PathVariable Long size) {
        if (userId == null) {
            return null;
        }
        return looperService.getUserLooperListPage(userId, current, size);
    }



    @ApiOperation("门户网站 - 私人空间 - 用户投放的所有轮播图")
    @GetMapping("/user/all/{userId}/{current}/{size}")
    public Result searchAll(@PathVariable Long userId,
                            @PathVariable Long current,
                            @PathVariable Long size) {
        if (userId == null) {
            return null;
        }
        return looperService.getUserAllLooperListPage(userId, current, size);
    }



}

