package com.backstage.dao;

import com.backstage.entity.SysRolePermission;
import com.backstage.entity.SysUserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-17 10:17
 **/
public interface SysUserRoleMapper extends Mapper<SysUserRole> {

    @Insert("<script>" +
            "insert into sys_user_role (id, user_id, role_id,create_time) values " +
            "<foreach collection=\"list\" item=\"userRole\" separator=\",\">" +
            "(#{userRole.id}, #{userRole.userId}, #{userRole.roleId}, #{userRole.createTime})" +
            "</foreach>"+
            "</script>")
    @Options(useGeneratedKeys = false)
    int insertUserRole(List<SysUserRole> userRole);
}
