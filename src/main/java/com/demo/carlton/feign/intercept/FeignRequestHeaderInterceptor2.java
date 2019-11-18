package com.demo.carlton.feign.intercept;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * need to consider retry mechanism.
 */

public class FeignRequestHeaderInterceptor2 implements RequestInterceptor {
    
    @Override
    public void apply(RequestTemplate template) {
        try {
            System.out.println("FeignRequestHeaderInterceptor2");
        } catch (Exception e) {

        }
    }
}
