package com.backstage.service.impl;

import com.backstage.dao.SysRolePermissionMapper;
import com.backstage.entity.SysRolePermission;
import com.backstage.service.SysRolePermissionService;
import com.backstage.unils.IdWorker;
import com.backstage.unils.text.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-16 16:36
 **/
@Service
@Transactional
@Slf4j
public class SysRolePermissionServiceImpl implements SysRolePermissionService {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Override
    public List<String> getRoleByPermission(String roleId) {

        SysRolePermission role=new SysRolePermission();
        role.setRoleId(roleId);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.select(role);

        List<String> result=new ArrayList<>();
        for (SysRolePermission sysRolePermission : sysRolePermissions) {
            result.add(sysRolePermission.getPermissionId());
        }
        return result;
    }

    @Override
    public void insert(String id, String[] menu) {
        Example example=new Example(SysRolePermission.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("roleId",id);

        sysRolePermissionMapper.deleteByExample(example);
        List<SysRolePermission> all=new ArrayList<>();
        for (int i = 0; i < menu.length; i++) {
            SysRolePermission sysRolePermission=new SysRolePermission();
            sysRolePermission.setRoleId(id);
            sysRolePermission.setCreateTime(new Date());
            sysRolePermission.setId(Convert.toStr(new IdWorker().nextId()));
            sysRolePermission.setPermissionId(menu[i]);
            all.add(sysRolePermission);
        }
        sysRolePermissionMapper.insertmenu(all);

    }

    @Override
    public List<String> getRoleByMenuPermission(List<String> roleId) {
        Example example=new Example(SysRolePermission.class);
        example.selectProperties("permissionId");
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("roleId",roleId);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.selectByExample(example);

        List<String> all=new ArrayList<>();
        for (SysRolePermission permission : sysRolePermissions) {
            all.add(permission.getPermissionId());
        }
        return all;
    }
    @Override
    public boolean isFindPer(String perId){
        Example example=new Example(SysRolePermission.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("permissionId",perId);
        int i = sysRolePermissionMapper.selectCountByExample(example);
        return i>0?true:false;
    }
}
