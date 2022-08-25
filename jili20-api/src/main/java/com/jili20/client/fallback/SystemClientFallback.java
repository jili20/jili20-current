package com.jili20.client.fallback;

import com.jili20.client.SystemClient;
import com.jili20.client.vo.UserMainInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author bing_  @create 2022/1/22-14:02
 */
@Slf4j
@Service
public class SystemClientFallback implements SystemClient {

    /**
     * 用户 - 留言回复目标 - 根据用户ID，查询用户：ID、用户名、头像、赞赏码
     *
     * @param userId
     * @return
     */
    @Override
    public UserMainInfoVo findUserMainInfo(Long userId) {
        log.error("远程调用失败，服务熔断，直接返回空");
        return null;
    }

}