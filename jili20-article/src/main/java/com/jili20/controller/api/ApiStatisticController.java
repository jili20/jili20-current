package com.jili20.controller.api;

import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 统计控制器 - 统计用户各项总数
 *
 * @author bing_  @create 2022/2/27-14:19
 */
@Api(tags = "API - 统计管理")
@RestController
@RequestMapping("/api/statistic")
public class ApiStatisticController {

    @Resource
    private StatisticService statisticService;

    @ApiOperation("门户网站 - 个人主页 - 统计用户各项总数（文章微服务）")
    @GetMapping("/total/{userId}")
    public Result statisticCount(@PathVariable("userId") Long userId) {
        if (userId == null) {
            return null;
        }
        Assert.isTrue(userId.toString().length() >0 && userId.toString().length() < 20, ResponseEnum.REQUEST_ID_LENGTH_ERROR);
        return statisticService.total(userId);
    }

}
