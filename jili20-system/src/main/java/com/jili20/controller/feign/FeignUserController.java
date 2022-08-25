package com.jili20.controller.feign;

import com.jili20.client.SystemClient;
import com.jili20.client.vo.UserMainInfoVo;
import com.jili20.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author bing_  @create 2022/1/22-16:26
 */
@Api(tags = "Feign - 被远程调用的 - 用户微服务接口")
@RestController
public class FeignUserController implements SystemClient {

    @Resource
    private SysUserService sysUserService;

    /**
     * 用户 - 根据用户ID，查询用户：ID、用户名、头像、赞赏码 - 被远程调用的接口
     * @param userId
     * @return
     */
    @Override
    public UserMainInfoVo findUserMainInfo(Long userId) {
        return sysUserService.findUserMainInfo(userId);
    }


}