package com.demo.carlton.feign;

import com.demo.carlton.entity.User;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "just-a-name-2", url = "http://127.0.0.1:8088" ,configuration = RemoteConfiguration2.class)
public interface RawFeign2 {

    // @RequestLine("POST /getUser2?id={id}")// @RequestLine 此注解是feign注解
    // https://blog.csdn.net/huaseven0527/article/details/80533983
    @RequestMapping(value = "/getUser2?id={id}", method = RequestMethod.POST)
    public User getUser(@Param("id") int id);

}
