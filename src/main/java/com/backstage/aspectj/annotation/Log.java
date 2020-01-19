package com.backstage.aspectj.annotation;

import com.backstage.aspectj.enums.BusinessType;

import java.lang.annotation.*;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-19 09:20
 **/
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /** 模块 */
    String title() default "";

    /** 功能 */
    BusinessType busionesstype() default BusinessType.INSERT;
}
