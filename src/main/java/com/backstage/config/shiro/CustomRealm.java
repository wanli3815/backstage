package com.backstage.config.shiro;

import com.backstage.entity.SysUser;
import com.backstage.entity.SysUserRole;
import com.backstage.exception.login.UserNotExistsException;
import com.backstage.service.*;
import com.google.code.kaptcha.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-13 17:26
 **/
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private  SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SysUser user=new SysUser();
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(principal!=null){
            BeanUtils.copyProperties(principal,user);
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        if(user.getId().equals("fcf34b56-a7a2-4719-9236-867495e74c31")){
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        }else{
            List<String> roleByUser = sysUserRoleService.getRoleByUser(user.getId());


            //添加角色数据
            info.addRoles(sysRoleService.getRoleName(roleByUser));

            //获取角色下的所有权限列表
            List<String> permission = sysRolePermissionService.getRoleByMenuPermission(roleByUser);

            //通过权限数据获取对应的授权码
            Set<String> perms = sysPermissionService.permissionPerms(permission);
            //测试期间先赋予所有数据权限
            info.setStringPermissions(perms);
        }



        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null)
        {
            password = new String(upToken.getPassword());
        }
        SysUser user=null;
        try {
            user = sysUserService.login(username, password);
        } catch (UserNotExistsException e)
        {
            throw new UnknownAccountException(e.getMessage(), e);
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;

    }

   /* @Autowired
    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //根据用户名去数据库查询用户信息
        User user = loginService.getUserByName(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for (Permissions permissions : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = loginService.getUserByName(name);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;
        }
    }*/
}