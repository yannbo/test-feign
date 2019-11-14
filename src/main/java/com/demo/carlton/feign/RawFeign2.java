package com.demo.carlton.feign;

import com.demo.carlton.entity.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.net.URI;

@FeignClient(name = "just-a-name-2")
public interface RawFeign2 {

	@RequestLine("POST /getUser2?id={id}")
	public User getUser(@Param("id") int id);

	@RequestLine("POST /getUser2?id={id}")
	public User getUser(URI uri, @Param("id") int id);

}
