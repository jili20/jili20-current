package com.jili20.filter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关限流过滤器
 * @author bing_  @create 2022/1/14-16:59
 */
@Component("uriKeyResolver")
public class uriKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // 针对微服务的每个请求进行限流
        return Mono.just(exchange.getRequest().getURI().getPath());
    }
}
