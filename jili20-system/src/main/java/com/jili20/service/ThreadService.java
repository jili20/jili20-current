package com.jili20.service;

import com.jili20.entity.SysUser;
import com.jili20.mapper.SysUserMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 多线程
 *
 * @author bing_  @create 2022/1/13-15:44
 */
@Component
public class ThreadService {

    /**
     * 用户登录 - 多线程更新用户最后登录时间、登录IP
     *
     * @param request
     * @param sysUserMapper
     * @param userId
     */
    public void updateUserLoginIpAndLastLoginTime(HttpServletRequest request, SysUserMapper sysUserMapper, String userId) {
        SysUser sysUser = sysUserMapper.selectById(Long.valueOf(userId));
        sysUser.setLoginIp(request.getRemoteAddr());
        sysUser.setLastLoginTime(LocalDateTime.now());
        sysUserMapper.updateById(sysUser);
    }

}
