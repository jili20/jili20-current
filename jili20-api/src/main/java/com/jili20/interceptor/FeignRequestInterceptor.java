package com.jili20.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用 Feign 进行远程调用时,先经过此拦截器,在此拦截器中将请求头带上访问令牌
 *
 * @author bing_  @create 2022/1/23-12:58
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        // 通过 RequestContextHolder 工具来获取请求相关变量
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            // 获取请求对象
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            // Bearer xxxxx
            if (StringUtils.isNotBlank(token)) {
                // 在使用 feign 远程调用时，请求头就会带上访问令牌
                requestTemplate.header(HttpHeaders.AUTHORIZATION, token);
            }
        }

    }
}

