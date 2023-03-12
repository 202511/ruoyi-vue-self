package com.ruoyi.system.ratelimiter;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    public String key() default  "rate_limit:";
    //默认同一个ip 10 秒内， 只能访问10 次
    public int time() default 10;
    public int count() default 10;
}
