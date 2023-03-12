package com.ruoyi.system.datascope;


import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.ruoyi.system.domin.LoginUser;
import com.ruoyi.system.domin.SysRoleMenu;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataScopeAspect {

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";


    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "2";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    @Before("@annotation(t)")
    public void doBefore(JoinPoint joinPoint, DataScope t) throws Throwable
    {

        System.out.println("对查询做出限制");
        // 查看当前用户的权限
        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //1  遍历 用户的角色 （满足当前方法操作权限的角色）
        String s = loginUser.getDataScope().equals("1") ? null : "menu_id = 1";
        //2 将所有满足条件的角色的对这个操作查询权限， 添加进现操作的查询权限中，


        //3 最后把查询权限添加进查询数据库的语句中。
        Object arg = joinPoint.getArgs()[0];
        SysRoleMenu arg1 = (SysRoleMenu) arg;
        if(s!=null)
        arg1.getParams().put("dataScope", s);
        System.out.println("限制成功");

    }

}
