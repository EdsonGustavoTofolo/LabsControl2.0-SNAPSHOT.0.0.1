package br.com.edsontofolo.labscontrol.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class ApplicationPreFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(ApplicationPreFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("My first pre-filter is executed..");

        ServerHttpRequest request = exchange.getRequest();

        String requestPath = request.getPath().toString();
        logger.info("Request path {}", requestPath);

        HttpHeaders headers = request.getHeaders();
        Set<String> headerNames = headers.keySet();
        headerNames.forEach(headerName -> {
            String headerValue = headers.getFirst(headerName);
            logger.info("Header: {} - Value: {}", headerName, headerValue);
        });

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
