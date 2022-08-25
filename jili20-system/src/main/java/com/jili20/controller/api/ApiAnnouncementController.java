package com.jili20.controller.api;

import com.jili20.result.Result;
import com.jili20.service.AnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author Bing
 * @since 2022-04-18
 */
@Api(tags = "API - 公告管理")
@RestController
@RequestMapping("/api/announcement")
public class ApiAnnouncementController {

    @Resource
    private AnnouncementService announcementService;


    @ApiOperation("门户网站 - 查询最新一条展示中公告")
    @GetMapping("/use")
    public Result getInUseOne() {
        return announcementService.getInUseOne();
    }

}

