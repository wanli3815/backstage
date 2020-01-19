package com.backstage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
@Data
public class SysUser  {
    @Id
    private String id;

    private String username;

    private String salt;

    private String password;

    private String phone;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "nick_name")
    private String nickName;

    private String email;

    private Integer status;

    private Integer sex;

    private Integer deleted;

    @Column(name = "create_id")
    private String createId;

    @Column(name = "update_id")
    private String updateId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return "SysUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +

                ", realName='" + realName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", sex=" + sex +
                ", deleted=" + deleted +
                ", createId='" + createId + '\'' +
                ", updateId='" + updateId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}