package com.backstage.service.impl;

import com.backstage.dao.SysLogMapper;
import com.backstage.entity.SysLog;
import com.backstage.service.SysLogService;
import com.backstage.unils.AddressUtils;
import com.backstage.unils.ServletUtils;
import com.backstage.unils.StringUtils;
import com.backstage.unils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-19 10:51
 **/

@Service
@Slf4j
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;


    @Override
    public List<SysLog> findAll(SysLog log) {
        Example example=new Example(SysLog.class);
        Example.Criteria criteria= example.createCriteria();
        if(StringUtils.isNotNull(log)){
            if(StringUtils.isNotEmpty(log.getUsername())){
                criteria.andEqualTo("username",log.getUsername());
            }
            if(StringUtils.isNotNull(log.getBusinessType())){
                criteria.andEqualTo("businessType",log.getBusinessType());
            }
        }
        String start = ServletUtils.getParameter("start");
        String end = ServletUtils.getParameter("end");
        if(StringUtils.isNotEmpty(start)){
            criteria.andGreaterThanOrEqualTo("createTime",start+" 00:00:00");
        }
        if(StringUtils.isNotEmpty(end)){
            criteria.andLessThanOrEqualTo("createTime",end+" 23:59:59");
        }

        List<SysLog> sysLogs = sysLogMapper.selectByExample(example);
        return sysLogs;
    }

    @Async
    @Override
    public void insertLog(SysLog logs) {
        logs.setIplocation(AddressUtils.getRealAddressByIP(logs.getIp()));
        sysLogMapper.insertSelective(logs);
    }
}
