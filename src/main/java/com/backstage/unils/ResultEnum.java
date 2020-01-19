package com.backstage.unils;

import lombok.Getter;

/**
 * @description: 返回ajax code 枚举
 * @author: wanli3815@163.com
 * @create: 2020-01-13 17:41
 **/
@Getter
public enum ResultEnum {
    //返回成功
    SUCCESS(1),
    //返回失败
    ERROR(2),
    ;
    private Integer code;

    ResultEnum(Integer code) {
        this.code = code;
    }
}
