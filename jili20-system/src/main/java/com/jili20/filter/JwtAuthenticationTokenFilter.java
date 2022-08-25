package com.jili20.filter;

import com.alibaba.fastjson.JSON;
import com.jili20.entity.security.LoginUser;
import com.jili20.result.ResponseEnum;
import com.jili20.result.Result;
import com.jili20.util.JwtUtil;
import com.jili20.util.RedisCache;
import com.jili20.util.WebUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * jwt 认证过滤器
 * Security 权限控制
 *
 * @author bing_  @create 2022/1/11-13:11
 * OncePerRequestFilter 保存请求只会经过此过滤器一次
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 登录认证通过，前端会把 token 放到请求头，所以这里从请求头中 获取 token
        // 获取 token
        final String path = request.getServletPath();


        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            // 直接放行（因为这个方法是解析 token ,没有登录，请求头没有 token，游客，操作需要权限的接口，其它过滤器会抛异常提示 ）
            filterChain.doFilter(request, response);
            return;
        }

        // 解析获取 userid
        Claims claims = null;

        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token 超时、token 非法
            // 响应告诉前端( 需要重新登录 )
            final Result result = Result.setResult(ResponseEnum.NEED_LOGIN_AGAIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        // 如果从请求头当中获取到用户 ID（请求头 token 只存放了用户ID)
        String userId = claims.getSubject();

        // 从 redis 中获取用户信息
        String redisKey = "login:" + userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        // 抛异常，用户未登录
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }
        // 使用三个参数的构造器，参数：用户、已认证状态、用户权限信息
        // 获取权限信息封装到 Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

        // 存入 SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
