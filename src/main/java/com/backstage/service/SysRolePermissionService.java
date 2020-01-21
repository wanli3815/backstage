package com.backstage.service;

import com.backstage.entity.SysPermission;

import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-16 16:35
 **/
public interface SysRolePermissionService {

    List<String> getRoleByPermission(String roleId);

    void insert(String id, String[] menu);

    /**
     * 根据list 角色id 获取 权限idlist
     * @param roleId
     * @return
     */
    List<String> getRoleByMenuPermission(List<String>roleId);

    boolean isFindPer(String perId);
}
