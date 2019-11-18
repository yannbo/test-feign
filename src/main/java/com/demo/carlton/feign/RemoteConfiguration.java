package com.demo.carlton.feign;

import com.demo.carlton.feign.intercept.FeignRequestHeaderInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RemoteConfiguration {

    @Bean
    public RequestInterceptor headerInterceptor() {
        return new FeignRequestHeaderInterceptor();
    }
}
