package com.jili20.controller.api;

import com.jili20.exception.Assert;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.service.SysUserService;
import com.jili20.vo.user.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bing_
 */
@Api(tags = "API - 用户管理")
@Slf4j
@RestController
@RequestMapping("/api/user")
public class ApiSysUserController {

    @Resource
    private SysUserService sysUserService;


    @ApiOperation("门户网站 - 登录 - 用户名和密码登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo vo,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        return sysUserService.login(vo, request, response);
    }


    @ApiOperation("门户网站 - 个人主页 - 根据路由用户ID - 获取用户信息")
    @GetMapping("/info/{userId}")
    public Result getUserInfoByRouteId(@PathVariable("userId") Long userId) {
        Assert.notNull(userId, ResponseEnum.USER_ID_NULL_ERROR);
        return sysUserService.getUserInfo(userId);
    }


}