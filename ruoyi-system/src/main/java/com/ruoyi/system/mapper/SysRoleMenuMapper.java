package com.ruoyi.system.mapper;



import com.ruoyi.system.datascope.DataScope;
import com.ruoyi.system.domin.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface SysRoleMenuMapper {
    @DataScope
    public int selectMenu(SysRoleMenu sysRoleMenu );
}
