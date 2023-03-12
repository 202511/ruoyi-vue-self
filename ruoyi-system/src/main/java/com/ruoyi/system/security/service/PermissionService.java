package com.ruoyi.system.security.service;


import com.ruoyi.system.domin.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("ss")
public class PermissionService {
     public boolean hasPermission(String permission)
     {
         System.out.println("开始认证");
           if(permission== null ) return false;
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            LoginUser loginUser =(LoginUser) authentication.getPrincipal();
         System.out.println(loginUser);
           if(loginUser==null )  return false;
           if(loginUser.getPermission().equals(permission)) return true;
         System.out.println("失败");
           return false ;
     }

}
