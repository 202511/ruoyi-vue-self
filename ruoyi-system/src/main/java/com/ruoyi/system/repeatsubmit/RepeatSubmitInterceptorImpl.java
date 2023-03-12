package com.ruoyi.system.repeatsubmit;





import com.alibaba.fastjson2.JSON;
import com.ruoyi.system.cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Component
public class RepeatSubmitInterceptorImpl  extends  RepeatSubmitInterceptor {
    public final String  REPEAT_PARAMS="repeatParams:";
    public final String REPEAT_TIME="repeatTime";
    @Autowired
    RedisCache redisCache;
    @Override
    public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation) {
        String nowParams=JSON.toJSONString(request.getParameterMap());  //现在方法的参数数据结构转字符串
        Map<String, Object> nowDataMap=new HashMap<>();
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis()); //现在的时间 以毫秒计算
        String requestURL = request.getRequestURI();
        System.out.println(requestURL);
        String authorization = request.getHeader("Authorization");
        String s = "repeat_submit:" + requestURL + authorization; // 作为存入redis的key
        Object cacheObject = redisCache.getCacheObject(s); //检查是否有相同的提交
        if(cacheObject!=null)
        {
            Map<String, Object> cacheObject1 = (Map<String, Object>) cacheObject;
            if(cacheObject1.containsKey(requestURL))
            {
                Map<String, Object> preDataMap = (Map<String, Object>)  cacheObject1.get(requestURL);
                if((Long)nowDataMap.get(REPEAT_TIME)-(Long)preDataMap.get(REPEAT_TIME) < annotation.value() )
                {
                    if(preDataMap.get(REPEAT_PARAMS).toString().equals(nowDataMap.get(REPEAT_PARAMS).toString()))
                    {
                         return  true;
                    }
                }
            }
        }
        //如果没有重复提交， 将这次的提交作为最新的提交
        Map<String ,Object> cacheMap =new HashMap<>();
        cacheMap.put(requestURL, nowDataMap);
        redisCache.setCacheObject(s, cacheMap, (int)annotation.value(), TimeUnit.MILLISECONDS);
        return false;
    }
}
