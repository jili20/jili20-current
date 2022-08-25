package com.jili20.client.fallback;

import com.jili20.client.ArticleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author bing_  @create 2022/3/5-16:31
 */
@Slf4j
@Service
public class ArticleClientFallback implements ArticleClient {


    @Override
    public Boolean updateUsername(String username, Long userId) {
        log.error("远程调用文章微服务更新用户名失败，服务熔断，直接返回 false");
        return false;
    }

    @Override
    public Boolean userUpdateAvatar(String avatar, Long userId) {
        log.error("远程调用文章微服务更新用户头像失败，服务熔断，直接返回 false");
        return false;
    }
}
