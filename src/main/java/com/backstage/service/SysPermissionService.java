package com.backstage.service;

import com.backstage.entity.SysPermission;
import com.backstage.vo.permission.Category;
import com.backstage.vo.permission.PermissionAdd;
import com.backstage.vo.permission.PermissionEdit;
import com.backstage.vo.user.Menu;

import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-16 10:13
 **/
public interface SysPermissionService {
    List<SysPermission> findAll();

    //树形菜单
    List<Category> getAll();

    int insert(PermissionAdd permissionAdd);

    SysPermission getInfo(String id);

    int update(PermissionEdit permissionEdit);

    boolean isFindSub(String id);

    int delete(String id);

    List<Menu> permissionTreeList(List<String> permissionId);

    Set<String> permissionPerms(List<String> permissionId);
}
