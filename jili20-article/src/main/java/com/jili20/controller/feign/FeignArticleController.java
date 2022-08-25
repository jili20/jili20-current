package com.jili20.controller.feign;

import com.jili20.client.ArticleClient;
import com.jili20.service.*;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author bing_  @create 2022/3/5-16:37
 */
@Api(tags = "Feign - 被远程调用的 - 用户微服务接口")
@RestController
public class FeignArticleController implements ArticleClient {

    @Resource
    private ArticleService articleService;


    /**
     * 远程调用文章微服务 - 用户修改用户名 - 门户网站 - 私人空间
     *
     * @param username
     * @param userId
     * @return
     */
    @Override
    public Boolean updateUsername(String username, Long userId) {
        return articleService.updateUsernameByUserId(username, userId);
    }


    /**
     * 远程调用文章微服务 - 用户修改头像 - 门户网站 - 私人空间
     *
     * @param avatar
     * @param userId
     * @return
     */
    @Override
    public Boolean userUpdateAvatar(String avatar, Long userId) {
        return articleService.userUpdateAvatar(avatar, userId);
    }


}
