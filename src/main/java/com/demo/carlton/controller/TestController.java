package com.demo.carlton.controller;

import com.demo.carlton.entity.FeignBean;
import com.demo.carlton.entity.User;
import com.demo.carlton.feign.RawFeign;
import com.demo.carlton.feign.RawFeign2;
import feign.Feign;
import feign.Feign.Builder;
import feign.Request.Options;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * See the detail explanation: <code>com.demo.carlton.feign.FeignInstance1</code>
 */
@RestController
// 【1】import FeignClientsConfiguration.class
@Import(FeignClientsConfiguration.class)
public class TestController {

    // 不需要 @Autowired
    RawFeign testFeign;

    @Autowired
    RawFeign2 rawFeign2;

    // 【2】 构造函数添加 @Autowired ，注入encoder，decoder，构建 TestFeign
    @Autowired
    public TestController(@Qualifier("headerInterceptor") RequestInterceptor feignRequestHeaderInterceptor, FeignBean feignBean, Encoder encoder, Decoder decoder) {
        // options方法指定连接超时时长及响应超时时长，retryer方法指定重试策略
        Builder builder = Feign.builder();
        // 设置http basic验证
        builder = builder.contract(new feign.Contract.Default());
        // 【3】设置编码，不然会报错feign.codec.EncodeException
        builder = builder.encoder(encoder).decoder(decoder);
        // options方法指定连接超时时长及响应超时时长，retryer方法指定重试策略
        builder = builder.options(new Options(feignBean.getOpion_conn(), feignBean.getOpion_read()))
                .retryer(new Retryer.Default(feignBean.getRetry_period(), feignBean.getRetry_maxPeriod(),
                        feignBean.getRetry_maxAttempts()));
        builder.requestInterceptor(feignRequestHeaderInterceptor);
        // 【4】 target 链接目标feing，并指定访问域名
        testFeign = builder.target(RawFeign.class, feignBean.getUrl());
//		testFeign = builder.target(Target.EmptyTarget.create(TestFeign.class));
        System.out.println(000);
    }

    @PostMapping("/getUser")
    public User getUser(@RequestParam(defaultValue = "1") int id) {
        System.out.println("id=" + id);
        User user = testFeign.getUser(id);
        return user;
    }

    @PostMapping("/getUser1")
    public User getUser1(@RequestParam(defaultValue = "1") int id) {
        System.out.println("id=" + id);
        User user = rawFeign2.getUser(id);
        return user;
    }

    @PostMapping("/getUser2")
    public User getUser2(@RequestParam(defaultValue = "1") int id) {
        System.out.println("id=" + id);
        User user = new User(id, "my name " + id, (id * 10));
        return user;
    }

}
