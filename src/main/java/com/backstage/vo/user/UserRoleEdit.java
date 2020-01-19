package com.backstage.vo.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;


/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-17 10:40
 **/
@Data
public class UserRoleEdit {


    @NotBlank(message = "请输入用户id")
    private String userId;

    @NotEmpty(message = "请输入角色id集合")
    private List<String> roleIds;
}
