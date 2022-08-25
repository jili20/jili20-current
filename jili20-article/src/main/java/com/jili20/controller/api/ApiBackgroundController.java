package com.jili20.controller.api;

import com.jili20.result.Result;
import com.jili20.service.BackgroundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 轮播图背景图表 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-04-02
 */
@Api(tags = "API - 轮播图背景图管理")
@RestController
@RequestMapping("/api/background")
public class ApiBackgroundController {


    @Resource
    private BackgroundService backgroundService;


    @ApiOperation("门户网站 - 首页 - 获取最新一张启用的轮播图背景图片")
    @GetMapping("/one")
    public Result getOne() {
        return backgroundService.getOnlyOne();
    }

}

