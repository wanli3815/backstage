package com.backstage.dao;

import com.backstage.entity.SysRolePermission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-16 16:34
 **/
public interface SysRolePermissionMapper extends Mapper<SysRolePermission> {

    //<foreach collection="list" item="item" index="index" separator=",">
    @Insert("<script>" +
            "insert into sys_role_permission (id, role_id, permission_id,create_time) values " +
            "<foreach collection=\"list\" item=\"menu\" separator=\",\">" +
            "(#{menu.id}, #{menu.roleId}, #{menu.permissionId}, #{menu.createTime})" +
            "</foreach>"+
            "</script>")
    @Options(useGeneratedKeys = false)
    int insertmenu(List<SysRolePermission> menu);
}
