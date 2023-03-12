package com.ruoyi.system.log;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OperLogAspect {

    @Before("@annotation(t)")
    public void  doBefore(OperLog t)
    {
        String value = t.value();
        //下面这一步 代替将记录存入数据库
        System.out.println("成功记录： "+ value);

    }



}
