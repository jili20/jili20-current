package com.jili20.client;

import com.jili20.client.fallback.ArticleClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bing_  @create 2022/3/5-16:30
 */
@FeignClient(value = "jili20-article", path = "/article", fallback = ArticleClientFallback.class)
public interface ArticleClient {

    /**
     * 远程调用文章微服务 - 用户修改用户名 - 门户网站 - 私人空间
     *
     * @param username
     * @param userId
     * @return
     */
    @PostMapping("/api/update/username")
    Boolean updateUsername(@RequestParam("username") String username, @RequestParam("userId") Long userId);


    /**
     * 远程调用文章微服务 - 用户修改头像 - 门户网站 - 私人空间
     *
     * @param avatar
     * @param userId
     * @return
     */
    @PostMapping("/api/update/avatar")
    Boolean userUpdateAvatar(@RequestParam("avatar") String avatar, @RequestParam("userId") Long userId);


}
