package com.jili20.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jili20.entity.SysUser;
import com.jili20.entity.security.LoginUser;
import com.jili20.mapper.SysMenuMapper;
import com.jili20.mapper.SysUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 自定义 UserDetailsService
 *
 * @author bing_  @create 2022/1/10-18:45
 * Security 权限控制
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));

        // 如果没有查询到用户就抛出异常
        if(Objects.isNull(sysUser)){
            throw new RuntimeException("用户名或者密码错误");
        }

        // 查询用户所拥有的权限信息
        List<String> list = sysMenuMapper.selectPermsByUserId(sysUser.getId());

        // 把数据封装成UserDetails返回
        return new LoginUser(sysUser, list);
    }
}