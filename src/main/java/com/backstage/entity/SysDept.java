package com.backstage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class SysDept  {
    @Id
    private String id;

    @Column(name = "dept_no")
    private String deptNo;

    private String name;

    private String pid;

    @Column(name = "pid_name")
    private String pidName;

    private Integer status;

    @Column(name = "relation_code")
    private String relationCode;

    @Column(name = "dept_manager_id")
    private String deptManagerId;

    @Column(name = "manager_name")
    private String managerName;

    private String phone;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private Integer deleted;

    @Override
    public String toString() {
        return "SysDept{" +
                "id='" + id + '\'' +
                ", deptNo='" + deptNo + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", pidName='" + pidName + '\'' +
                ", status=" + status +
                ", relationCode='" + relationCode + '\'' +
                ", deptManagerId='" + deptManagerId + '\'' +
                ", managerName='" + managerName + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}