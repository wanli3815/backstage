package com.backstage.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
@Data
public class SysLog {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    private String username;

    private String operation;

    private  String url;

    private Integer time;

    private  String iplocation;

    @Column(name = "business_type")
    private Integer businessType;

    private String method;

    private String params;

    private String ip;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    @Override
    public String toString() {
        return "SysLog{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", operation='" + operation + '\'' +
                ", time=" + time +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}