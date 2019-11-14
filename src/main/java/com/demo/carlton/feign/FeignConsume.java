package com.demo.carlton.feign;

import com.demo.carlton.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class FeignConsume {

    @Autowired
    FeignInstance1 feign;

    public User getSomeThing() {
        return feign.getInstanceFeignClient().getUser(1);
    }
}
