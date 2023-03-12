package com.ruoyi.system.filter;

import com.ruoyi.system.cache.RedisCache;
import com.ruoyi.system.domin.LoginUser;
import com.ruoyi.system.token.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    private  String tokenPrefix="Bearer ";
    @Value("${token.headName}")
    private String headName;
   @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(headName);
        if(header!=null &&header.startsWith(tokenPrefix)==true)
        {
            header=header.replace(tokenPrefix,"");
            Claims valid = jwtService.isValid(header);
            String key = (String)valid.get("userId");
            LoginUser loginUser = redisCache.getCacheObject(key);
            System.out.println(loginUser);
//            接下来只要把这个 对象注入到请求的上下文中， 我们就完成了。
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
