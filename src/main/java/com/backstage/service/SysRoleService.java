package com.backstage.service;

import com.backstage.entity.SysRole;
import com.backstage.vo.role.RoleAdd;
import com.backstage.vo.role.RoleEdit;

import java.util.List;

/**
 * @description: 角色Service
 * @author: wanli3815@163.com
 * @create: 2020-01-15 16:41
 **/
public interface SysRoleService {
    List<SysRole> findAll();

    int insert(RoleAdd role);

    int update(RoleEdit role);

    SysRole getRole(String id);

    List<String> getRoleName(List<String> roleId);
}
