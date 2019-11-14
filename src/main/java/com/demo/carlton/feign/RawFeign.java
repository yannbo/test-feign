package com.demo.carlton.feign;

import com.demo.carlton.entity.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.net.URI;

/**
 * The raw feign can be used as a basic feign to call remote api directly.
 * if need to add strict policy, need to target to concrete feign.
 * sample: <code>com.demo.carlton.feign.FeignInstance1</code>
 */
@FeignClient(name = "just-a-name", url = "")
public interface RawFeign {

    @RequestLine("POST /getUser2?id={id}")
    public User getUser(@Param("id") int id);

    @RequestLine("POST /getUser2?id={id}")
    public User getUser(URI uri, @Param("id") int id);

}
