package com.demo.carlton.feign.intercept;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * need to consider retry mechanism.
 */
public class FeignRequestHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        try {
            System.out.println("FeignRequestHeaderInterceptor");
        } catch (Exception e) {

        }
    }
}
