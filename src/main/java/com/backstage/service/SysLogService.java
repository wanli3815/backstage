package com.backstage.service;

import com.backstage.entity.SysLog;

import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-19 10:50
 **/
public interface SysLogService {
    List<SysLog> findAll(SysLog log);

    void insertLog(SysLog log);
}
