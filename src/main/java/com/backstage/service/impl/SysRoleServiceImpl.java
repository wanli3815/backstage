package com.backstage.service.impl;

import com.backstage.dao.SysRoleMapper;
import com.backstage.entity.SysRole;
import com.backstage.service.SysRoleService;
import com.backstage.unils.IdWorker;
import com.backstage.unils.text.Convert;
import com.backstage.vo.role.RoleAdd;
import com.backstage.vo.role.RoleEdit;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-15 16:42
 **/
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public List<SysRole> findAll() {
        List<SysRole> sysRoles = sysRoleMapper.selectAll();
        return sysRoles;
    }

    @Override
    public int insert(RoleAdd role) {
        SysRole sysRole=new SysRole();
        BeanUtils.copyProperties(role,sysRole);
        sysRole.setId(Convert.toStr(new IdWorker().nextId()));
        sysRole.setCreateTime(new Date());
        sysRole.setUpdateTime(new Date());
        int i = sysRoleMapper.insertSelective(sysRole);
        return i;
    }

    @Override
    public int update(RoleEdit role) {
        SysRole sysRole=new SysRole();
        BeanUtils.copyProperties(role,sysRole);
        sysRole.setUpdateTime(new Date());
        int i = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        return i;
    }

    @Override
    public SysRole getRole(String id) {
        Example example=new Example(SysRole.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("id",id);
        SysRole sysRole = sysRoleMapper.selectOneByExample(example);
        return sysRole;
    }

    @Override
    public List<String> getRoleName(List<String> roleId) {
        Example example=new Example(SysRole.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("id",roleId);
        List<SysRole> sysRoles = sysRoleMapper.selectByExample(example);
        List<String> all=new ArrayList<>();
        for (SysRole role : sysRoles) {
            all.add(role.getName());
        }
        return all;
    }
}
