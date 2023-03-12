package com.ruoyi.system.security.handle;

import com.alibaba.fastjson2.JSON;
import org.springframework.core.serializer.Serializer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint , Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        System.out.println("认证失败");
    }
}
