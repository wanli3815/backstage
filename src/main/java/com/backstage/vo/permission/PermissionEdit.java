package com.backstage.vo.permission;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-16 14:23
 **/
@Data
public class PermissionEdit {

    @NotBlank(message = "id 不能为空")
    private String id;

    private String code;

    @NotBlank(message = "请输入菜单姓名")
    private String name;

    private String perms;

    private String url;

    @NotBlank(message = "请选择父类别")
    private String pid;

    private Integer orderNum;

    @NotNull(message = "请选择类型")
    private Integer type;

    private Integer status;
}
