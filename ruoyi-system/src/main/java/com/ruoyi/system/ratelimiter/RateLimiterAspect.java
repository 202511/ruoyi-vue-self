package com.ruoyi.system.ratelimiter;


import com.ruoyi.system.cache.RedisCache;
import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@Aspect
@Component
public class RateLimiterAspect {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisScript<Long> longRedisScript;
    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint joinPoint , RateLimiter rateLimiter)
    {
        int time = rateLimiter.time();
        int count = rateLimiter.count();
        String combineKey = getCombineKey(rateLimiter, joinPoint);
        List<String> strings = Collections.singletonList(combineKey);
        try
        {
            Long number =(Long) redisTemplate.execute(longRedisScript, strings, count, time);
            if(number.intValue() > count )
            {
                throw new RuntimeException("服务器限流异常， 请稍后再试");
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("服务器限流异常");
        }
    }
    public String getCombineKey(RateLimiter rateLimiter,  JoinPoint  joinPoint)
    {
        StringBuffer stringBuffer = new StringBuffer(rateLimiter.key());
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //获取请求的ip地址
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }

        System.out.println(ip);

        Method method = signature.getMethod();
        String name = method.getDeclaringClass().getName();
        String name1 = method.getName();
        stringBuffer.append(ip+"-");
        stringBuffer.append(name+ "-");
        stringBuffer.append(name1);
        return stringBuffer.toString();

    }



}
