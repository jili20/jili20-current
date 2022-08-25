package com.jili20.client;

import com.jili20.client.fallback.SystemClientFallback;
import com.jili20.client.vo.UserMainInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 被远程调用的接口 - 调用用户微服务
 *
 * @author bing_  @create 2022/1/22-14:00
 */
@FeignClient(value = "jili20-system", path = "/system", fallback = SystemClientFallback.class)
public interface SystemClient {


    /**
     * 用户 - 留言回复目标 - 根据用户ID，查询用户：ID、用户名、头像、赞赏码
     *
     * @param userId
     * @return
     */
    @ApiOperation("用户 - 留言回复目标 - 根据用户ID，查询用户：ID、用户名、头像、赞赏码")
    @GetMapping("/api/user/{userId}")
    UserMainInfoVo findUserMainInfo(@PathVariable("userId") Long userId);


}
