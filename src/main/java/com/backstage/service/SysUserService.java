package com.backstage.service;

import com.backstage.entity.SysUser;
import com.backstage.vo.user.UserAdd;
import com.backstage.vo.user.UserEdit;

import java.util.List;

/**
 * @description: 后台用户接口
 * @author: wanli3815@163.com
 * @create: 2020-01-13 17:19
 **/
public interface SysUserService {

   SysUser login(String userName,String passWord);

   List<SysUser> findList(SysUser user);

   int insert(UserAdd user);

   int userExist(String userName);

   SysUser userInfo(String userId);

   int update(UserEdit userEdit);

   int changePwd(SysUser user);

   boolean delUser(String id);
}
