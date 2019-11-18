package com.demo.carlton.feign;

import com.demo.carlton.feign.intercept.FeignRequestHeaderInterceptor2;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RemoteConfiguration2 {

    @Bean
    public RequestInterceptor headerInterceptor2() {
        return new FeignRequestHeaderInterceptor2();
    }
}
