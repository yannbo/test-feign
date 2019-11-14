package com.demo.carlton.feign;

import com.demo.carlton.entity.FeignBean;
import feign.*;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * <p><code>TestFeign</code> is raw feign, the class which target the raw feign can
 * add the detail policy to the feign(add encoder and timeout,intercept etc).
 *
 * <p> the instance feign can as a feign factory to produce/comprise different feign used raw feign.
 *
 * The detail feign need meet the following conditions:
 * 1. Add annotation: <code>@Component, @Import(FeignClientsConfiguration.class)</code>
 * 2. <code>builder.target(RawFeign.class)</code>
 */
@Component
@Import(FeignClientsConfiguration.class)
public class FeignInstance1 {
    //注入encoder，decoder，构建 TestFeign
    @Autowired
    private FeignBean feignBean;

    @Autowired
    private Encoder encoder;

    @Autowired
    private Decoder decoder;

    public RawFeign getInstanceFeignClient() {
        // options方法指定连接超时时长及响应超时时长，retryer方法指定重试策略
        Feign.Builder builder = Feign.builder();
        // 设置http basic验证
        builder = builder.contract(new feign.Contract.Default()).requestInterceptor(
                new BasicAuthRequestInterceptor(feignBean.getAdminName(), feignBean.getAdminPassword()));
        // 【3】设置编码，不然会报错feign.codec.EncodeException
        builder = builder.encoder(encoder).decoder(decoder);
        // options方法指定连接超时时长及响应超时时长，retryer方法指定重试策略
        builder = builder.options(new Request.Options(feignBean.getOpion_conn(), feignBean.getOpion_read()))
                .retryer(new Retryer.Default(feignBean.getRetry_period(), feignBean.getRetry_maxPeriod(),
                        feignBean.getRetry_maxAttempts()));
        // 【4】 target 链接目标feing，并指定访问域名
        return builder.target(RawFeign.class, feignBean.getUrl());
    }

    public RawFeign2 getInstanceFeign2Client() {
        // options方法指定连接超时时长及响应超时时长，retryer方法指定重试策略
        Feign.Builder builder = Feign.builder();
        // 设置http basic验证
        builder = builder.contract(new feign.Contract.Default()).requestInterceptor(
                new BasicAuthRequestInterceptor(feignBean.getAdminName(), feignBean.getAdminPassword()));
        // 【3】设置编码，不然会报错feign.codec.EncodeException
        builder = builder.encoder(encoder).decoder(decoder);
        // options方法指定连接超时时长及响应超时时长，retryer方法指定重试策略
        builder = builder.options(new Request.Options(feignBean.getOpion_conn(), feignBean.getOpion_read()))
                .retryer(new Retryer.Default(feignBean.getRetry_period(), feignBean.getRetry_maxPeriod(),
                        feignBean.getRetry_maxAttempts()));
        // 【4】 target 链接目标feing，并指定访问域名
        return builder.target(RawFeign2.class, feignBean.getUrl());
    }
}
