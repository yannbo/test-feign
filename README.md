#介绍
- feign 远程访问api，动态url
- 使用feign结合springboot，实现httpClient的功能
- **The all case have been verified (11/18/2019)**

# Summary: How to use
## M1: raw client
### 在@Configuration中注入的所有intercept都会拦截当前feign请求。
1. `@EnableFeignClients` 
 ```
    @SpringBootApplication
    @EnableFeignClients     / 1 
    public class Application {
```
2.  @FeignClient &&  @RequestMapping
```
    @FeignClient(name = "just-a-name", url = "http://127.0.0.1:8088")
    public interface RawFeign {
        @RequestMapping(value = "/xxx?id={id}", method = RequestMethod.POST)  / 2
        public User getXXX(@Param("id") int id);
    }
```
##  M2: Custom Client
### 当raw client target被绑定到具体的feign(即添加了detail策略)后，默认的intercept被覆盖，即在@Configuration中注入的intercept不会拦截当前feign请求。
1. `@EnableFeignClients` 
 ```
    @SpringBootApplication
    @EnableFeignClients     / 1 
    public class Application {
```
2. Define raw client
``` 
@FeignClient(name = "just-a-name", url = "")
public interface RawFeign {
    @RequestLine("POST /getUser2?id={id}")
    public User getUser(@Param("id") int id);
```
3. - `builder.target(RawFeign.class, feignBean.getUrl());`
   - The class which target the raw feign can add the detail policy to the feign(add encoder and timeout,intercept etc).
``` 
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
``` 
4. Note: 当通过 `builder.target(RawFeign.class` 定制feign client时, `@FeignClient(configuration = XXX.class)` configuration 属性失效。

### RAW client best practice
    + 对于只是用raw client来说，当在java配置(XXX.class)中定义Fegin相关的属性时，对所有Feign client生效。
    + 如果只是对单个client生效，不要在配置文件中(XXX.class)声明@Component，在 client中配置 `@FeignClient(configuration = XXX.class)`.

[If this configuration class is on the component scan path, it'll be also picked up as general configuration. This means that a configuration class like this, 
when also scanned by our automatic component scan, will override all of the beans for each and every FeignClient, not just the one which defined it as configuration.]
也就是说，要是被 automatic component扫描到了，所有的FeignClient都会按照这个class的配置项去生效，而不是仅仅configuration = XXXConfiguration.class 这个显示声明的接口.
