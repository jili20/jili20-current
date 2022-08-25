package com.jili20.util;

import com.jili20.entity.security.LoginUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author bing_  @create 2022/1/13-23:08
 */
public class AuthUtil {

    /**
     * 获取当前登录用户 - ID
     * @return
     */
    public static Long getAuthUserId(){
        // 获取 SecurityContextHolder 中的用户 id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser().getId();
    }


    /**
     * 获取当前登录用户 - 用户名
     * @return
     */
    public static String getAuthUsername(){
        // 获取 SecurityContextHolder 中的用户 id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser().getUsername();

    }


    /**
     * 获取当前登录用户 - 状态
     * @return
     */
    public static String getAuthUserStatus(){
        // 获取 SecurityContextHolder 中的用户 id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser().getStatus();

    }


    /**
     * 获取当前登录用户 - 头像
     * @return
     */
    public static String getAuthUserAvatar(){
        // 获取 SecurityContextHolder 中的用户 id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser().getAvatar();
    }


}
