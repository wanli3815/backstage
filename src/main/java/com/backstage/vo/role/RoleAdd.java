package com.backstage.vo.role;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-15 17:24
 **/
@Data
public class RoleAdd {
    @NotBlank(message = "请输入角色姓名")
    private String name;

    @NotBlank(message = "请输入角色描述")
    private String description;
}
