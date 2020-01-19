package com.backstage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class SysPermission {
    @Id
    private String id;

    private String code;

    private String name;

    private String perms;

    private String url;

    private String method;

    private String pid;

    @Column(name ="order_num" )
    private Integer orderNum;

    private Integer type;

    private Integer status;

    @Column(name ="create_time" )
    private Date createTime;

    @Column(name ="update_time" )
    private Date updateTime;

    private Integer deleted;

    @Override
    public String toString() {
        return "SysPermission{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", perms='" + perms + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", pid='" + pid + '\'' +
                ", orderNum=" + orderNum +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}