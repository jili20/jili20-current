package com.jili20.entity.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bing_  @create 2022/2/8-15:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVo {

    /**
     * 登录成功生成的 jwt token
     */
    private String token;

    /**
     * 认证通过用户信息
     */
    private UserInfoVo userInfo;
}