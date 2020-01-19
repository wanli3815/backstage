package com.backstage.controller;

import com.backstage.entity.SysLog;
import com.backstage.service.SysLogService;
import com.backstage.unils.AjaxResult;
import com.backstage.unils.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-19 10:45
 **/
@Controller
@RequestMapping("/admin/log")
public class LogController extends BaseController {
    private String prefix="admin/log";

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/index")
    public String index(){
        return prefix+"/index";
    }

    @RequiresPermissions("system:log:list")
    @GetMapping("/getList")
    @ResponseBody
    public TableDataInfo list(SysLog log){
        startPage();
        List<SysLog> all = sysLogService.findAll(log);
        return getDataTable(all);
    }
}
