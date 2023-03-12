package com.ruoyi.system.repeatsubmit;


import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {
      @Override
      public boolean preHandle(HttpServletRequest  request , HttpServletResponse response, Object handler) throws  Exception
      {
            if(handler instanceof HandlerMethod)
            {
                  HandlerMethod handler1 = (HandlerMethod) handler;
                  Method method = handler1.getMethod();
                  RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
                  if(annotation!=null&& this.isRepeatSubmit(request, annotation))
                  {
                           System.out.println("请不要重复提交");
                            return false ;
                  }
            }
            return true;
      }
      //如果是重复提交返回true
      public abstract boolean  isRepeatSubmit(HttpServletRequest request , RepeatSubmit  annotation);

}
