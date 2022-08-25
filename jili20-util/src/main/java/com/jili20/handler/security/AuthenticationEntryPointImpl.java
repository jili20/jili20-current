package com.jili20.handler.security;

import com.alibaba.fastjson.JSON;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.util.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bing_  @create 2022/1/12-11:54
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 打印错误信息，方便定位错误提示
        authException.printStackTrace();

        // 如果是 InsufficientAuthenticationException  、 BadCredentialsException 异常，抛出错误提示
        Result result = null;
        if (authException instanceof BadCredentialsException) {
            // 用户名或密码错误【这个异常在这里捕捉不了，在全局异常处理类中处理成功】
            result = Result.build(ResponseEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        } else if (authException instanceof InsufficientAuthenticationException) {
            // 需要登录后操作
            result = Result.setResult(ResponseEnum.NEED_LOGIN);
        } else {
            result = Result.build(ResponseEnum.SYSTEM_ERROR.getCode(), "认证或授权失败");
        }

        // 处理异常
        WebUtils.renderString(response,JSON.toJSONString(result));
    }
}
