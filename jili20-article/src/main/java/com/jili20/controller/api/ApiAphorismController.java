package com.jili20.controller.api;


import com.jili20.result.Result;
import com.jili20.service.AphorismService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 随机诗语 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-01-18
 */
@Api(tags = "API - 随机诗语管理")
@RestController
@RequestMapping("/api/aphorism")
public class ApiAphorismController {

    @Resource
    private AphorismService aphorismService;


    /**
     * 注解 @Cache(expire = 60 * 60 * 1000,name = "aphorism") // aop 缓存1小时
     * 使用 aop 缓存5分钟 Cache(expire = 5 * 60 * 1000,name = "aphorism")
     */
    @ApiOperation("门户网站 - 首页 - 随机获取1条随机诗语")
    @GetMapping("/random")
    public Result getRandomTip() {
        return aphorismService.getRandomTip();
    }


}

