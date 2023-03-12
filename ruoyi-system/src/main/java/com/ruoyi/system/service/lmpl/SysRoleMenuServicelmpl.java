package com.ruoyi.system.service.lmpl;

import com.ruoyi.system.domin.SysRoleMenu;
import com.ruoyi.system.mapper.SysRoleMenuMapper;
import com.ruoyi.system.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysRoleMenuServicelmpl implements SysRoleMenuService {
     @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int select(SysRoleMenu sysRoleMenu) {

        return sysRoleMenuMapper.selectMenu(sysRoleMenu);
    }


}
