package com.backstage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @description: 通知
 * @author: wanli3815@163.com
 * @create: 2020-01-20 11:41
 **/
@Data
public class Notice {
    @Id
    private  String id;

    @NotBlank(message = "请输入标题")
    private String title;

    @NotBlank(message = "请输入作者")
    private String author;

    private  String content;

    private  String picture;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

}
