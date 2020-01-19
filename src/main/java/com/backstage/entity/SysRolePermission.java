package com.backstage.entity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
@Data
public class SysRolePermission  {
    @Id
    private String id;

    @Column(name ="role_id" )
    private String roleId;

    @Column(name ="permission_id" )
    private String permissionId;

    @Column(name ="create_time" )
    private Date createTime;

    @Override
    public String toString() {
        return "SysRolePermission{" +
                "id='" + id + '\'' +
                ", roleId='" + roleId + '\'' +
                ", permissionId='" + permissionId + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}