package com.backstage.vo.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @description: 用户编辑类
 * @author: wanli3815@163.com
 * @create: 2020-01-15 15:46
 **/
@Data
public class UserEdit {

    @NotBlank(message = "ID不能为空")
    private String id;

    @NotBlank(message = "账号不能为空")
    private String username;

    @NotBlank(message = "姓名不能为空")
    private  String realName;
    //正则判断手机号是否合法
    @Pattern(regexp = "^1(3|4|5|7|8|6)\\d{9}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    private String sex;
}
