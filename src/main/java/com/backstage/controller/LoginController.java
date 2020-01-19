package com.backstage.controller;

import com.backstage.unils.AjaxResult;
import com.google.code.kaptcha.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-13 16:31
 **/
@Controller
@Slf4j
@RequestMapping("/admin")
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }


    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxlogin(String username,String password,String vercode){
        Object obj = SecurityUtils.getSubject().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String code = String.valueOf(obj != null ? obj : "");
        if(!code.equalsIgnoreCase(vercode)){
            return AjaxResult.error("验证码输入错误");
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
            return AjaxResult.success();
        }catch (UnknownAccountException e){
            String msg = "用户或密码错误";

            return AjaxResult.error(msg);
        }

    }

}
