package com.backstage.exception.login;

/**
 * @description: 用户不存在的异常
 * @author: wanli3815@163.com
 * @create: 2020-01-14 14:13
 **/
public class UserNotExistsException extends RuntimeException {

    public UserNotExistsException(){
        super("user.not.exists", null);
    }
}
