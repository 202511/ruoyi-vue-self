package com.ruoyi.system.Controller;

import com.ruoyi.system.cache.RedisCache;
import com.ruoyi.system.domin.LoginUser;
import com.ruoyi.system.domin.SysRoleMenu;
import com.ruoyi.system.excel.ExcelUtil;
import com.ruoyi.system.log.OperLog;
import com.ruoyi.system.ratelimiter.RateLimiter;
import com.ruoyi.system.repeatsubmit.RepeatSubmit;
import com.ruoyi.system.service.SysRoleMenuService;
import com.ruoyi.system.token.JwtService;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class test {
    @Autowired
  private RedisCache redisCache;
    @Autowired
    private JwtService jwtService;
    @Autowired
      SysRoleMenuService sysRoleMenuService;
    private  String tokenPrefix="Bearer ";
    @Value("${token.headName}")
    private String headName;


    @RepeatSubmit
    @RateLimiter
    @PreAuthorize("@ss.hasPermission('system:get:tes')")
    @OperLog("获取角色和菜单关系")
    @GetMapping("/test")
    public void get(HttpServletResponse response) throws IOException, IllegalAccessException {
        SysRoleMenu sysRoleMenu=new SysRoleMenu();
        ExcelUtil<SysRoleMenu> s = new ExcelUtil<>();
        long o=1;
        sysRoleMenu.setMenuId(o);
        sysRoleMenu.setRoleId(o);
        SysRoleMenu sysRoleMenu1=new SysRoleMenu();
        sysRoleMenu1.setMenuId(o);
        sysRoleMenu1.setRoleId(o);
        ArrayList<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        sysRoleMenus.add(sysRoleMenu);
        sysRoleMenus.add(sysRoleMenu1);
        s.export("菜单和角色的关系",response,SysRoleMenu.class,sysRoleMenus);
    }

    @OperLog("登录")
    @GetMapping("/you")
    public String login()
    {
        String uuid="111";
        LoginUser loginUser = new LoginUser();
        loginUser.setUserName("www");
        loginUser.setPassword("123456");
        loginUser.setPermission("system:get:tes");
        loginUser.setDataScope("2");
        redisCache.setCacheObject(uuid, loginUser);
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", uuid);
        String jwt = jwtService.getJwt(map);
        String token=tokenPrefix+jwt;
        return token;
    }

}
