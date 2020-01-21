package com.backstage.service;

import com.backstage.entity.SysUserRole;
import com.backstage.vo.user.UserRoleEdit;

import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-17 10:20
 **/
public interface SysUserRoleService {
    List<String> getRoleByUser(String userId);

    int insert(UserRoleEdit userRoleEdit);

    void delRoleByUser(String userId);

    //根据角色查找用户数量 如果用户数量不等于零。提示先删除用户 在删除用户角色
    boolean isUserCountByRole(String roleId);

}
