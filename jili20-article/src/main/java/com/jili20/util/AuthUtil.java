package com.jili20.util;

import com.jili20.entity.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author bing_  @create 2022/1/13-23:08
 */
public class AuthUtil {

    public static LoginUser getLoginUser() {
        // 调用下面的方法，获取 SecurityContextHolder 中用户信息
        return (LoginUser) getAuthentication().getPrincipal();
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    // TODO 写站长的 ID
    public static Boolean isAdmin() {
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    /**
     * 获取当前登录用户 - ID
     *
     * @return
     */
    public static Long getAuthUserId() {
        // 调用上面的方法，获取 SecurityContextHolder 中的用户 id
        return getLoginUser().getUser().getId();
    }


    /**
     * 获取当前登录用户 - 用户名
     *
     * @return
     */
    public static String getAuthUsername() {
        // 调用上面的方法，获取 SecurityContextHolder 中的用户名
        return getLoginUser().getUser().getUsername();
    }


    /**
     * 获取当前登录用户 - 状态
     *
     * @return
     */
    public static String getAuthUserStatus() {
        // 调用上面的方法，获取 SecurityContextHolder 中的用户状态
        return getLoginUser().getUser().getStatus();

    }


    /**
     * 获取当前登录用户 - 头像
     *
     * @return
     */
    public static String getAuthUserAvatar() {
        // 调用上面的方法，获取 SecurityContextHolder 中的用户头像
        return getLoginUser().getUser().getAvatar();
    }


}
