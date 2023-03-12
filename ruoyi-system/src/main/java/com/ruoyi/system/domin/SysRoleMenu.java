package com.ruoyi.system.domin;

import com.ruoyi.system.datascope.BaseEntity;
import com.ruoyi.system.excel.Excel;
import org.springframework.stereotype.Component;


@Component
public class SysRoleMenu extends BaseEntity  {
    @Excel(name = "角色编号")
    private Long roleId;
    @Excel(name ="菜单编号")
    private Long menuId;
    public Long getRoleId(){return roleId;}

    public Long getMenuId() {
        return menuId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "SysRoleMenu{" +
                "roleId=" + roleId +
                ", menuId=" + menuId +
                '}';
    }
}
