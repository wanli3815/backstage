package com.backstage.exception;

import com.backstage.constants.Constants;
import com.backstage.unils.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;

/**
 * @description: 接口返回异常类
 * @author: wanli3815@163.com
 * @create: 2020-01-18 10:39
 **/
@Slf4j
@RestControllerAdvice
public class RestException {

    //AuthorizationException
    @ExceptionHandler(value = AuthorizationException.class)
    public AjaxResult authority (AuthorizationException e){
        return AjaxResult.error(Constants.NO_AUTH);
    }
    //SQLSyntaxErrorException
    @ExceptionHandler(value = SQLSyntaxErrorException.class)
    public AjaxResult sqlErrot (SQLSyntaxErrorException e){
        log.error("SQLSyntaxErrorException:{}", e);
        return AjaxResult.error(Constants.DATABASE_ERROR);
    }
    @ExceptionHandler(value = Exception.class)
    public AjaxResult exceptionerror(Exception e){
        log.error("Exception:{}",e);
        return AjaxResult.error(Constants.EXPMESSAGE);
    }
}
