package com.backstage.controller;

import com.backstage.aspectj.annotation.Log;
import com.backstage.aspectj.enums.BusinessType;
import com.backstage.entity.SysPermission;
import com.backstage.entity.SysRole;
import com.backstage.service.SysPermissionService;
import com.backstage.service.SysRolePermissionService;
import com.backstage.service.SysRoleService;
import com.backstage.unils.AjaxResult;
import com.backstage.unils.StringUtils;
import com.backstage.unils.page.TableDataInfo;
import com.backstage.vo.permission.Category;
import com.backstage.vo.role.Auth;
import com.backstage.vo.role.RoleAdd;
import com.backstage.vo.role.RoleEdit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 角色控制类
 * @author: wanli3815@163.com
 * @create: 2020-01-15 16:35
 **/
@Slf4j
@Controller
@RequestMapping("admin/role")
public class RoleController  extends  BaseController{
    private String prefix="admin/role";

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @GetMapping("/index")
    public String index(){
        return prefix+"/index";
    }

    @Log(title = "角色管理",busionesstype = BusinessType.LIST)
    @RequiresPermissions("system:role:list")
    @GetMapping("/roleList")
    @ResponseBody
    public TableDataInfo list(){
        startPage();
        List<SysRole> all = sysRoleService.findAll();
        return getDataTable(all);
    }

    @GetMapping("/add")
    public String add(){
        return prefix+"/add";
    }

    @Log(title = "角色管理",busionesstype = BusinessType.INSERT)
    @RequiresPermissions("system:role:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addPost(@Valid RoleAdd role, BindingResult result){
        if(result.hasErrors()){
            FieldError error=result.getFieldErrors().get(0);
            return AjaxResult.error(error.getDefaultMessage());
        }
        int i=sysRoleService.insert(role);
        if(i>0){
            return AjaxResult.success("操作成功");
        }
        return AjaxResult.error("操作失败");
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id")String id, ModelMap mmp){
        SysRole role = sysRoleService.getRole(id);
        mmp.put("role",role);
        return prefix+"/edit";
    }

    @Log(title = "角色管理",busionesstype = BusinessType.UPDATE)
    @RequiresPermissions("system:role:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editPost(@Valid RoleEdit role, BindingResult result){
        if(result.hasErrors()){
            FieldError error=result.getFieldErrors().get(0);
            return AjaxResult.error(error.getDefaultMessage());
        }
        int i=sysRoleService.update(role);
        if(i>0){
            return AjaxResult.success("操作成功");
        }
        return AjaxResult.error("操作失败");
    }
    @GetMapping("/auth/{id}")
    public String auth(@PathVariable("id") String id,ModelMap mmp){

        mmp.put("id",id);
        return prefix+"/auth";
    }

    @RequiresPermissions("system:role:auth")
    @PostMapping("/getauth/{id}")
    @ResponseBody
    public AjaxResult authPost(@PathVariable("id") String id){
        Auth auth=new Auth();
        List<SysPermission> all = sysPermissionService.findAll();
        List<String> roleByPermission = sysRolePermissionService.getRoleByPermission(id);
        auth.setList(all);
        auth.setChecked(roleByPermission);
        return AjaxResult.success("",auth);
    }

    @Log(title = "角色管理",busionesstype = BusinessType.AUTH)
    @RequiresPermissions("system:role:auth")
    @PostMapping("/auth")
    @ResponseBody
    public AjaxResult authPostSend(String id, String menuids){
        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(menuids)){
            return AjaxResult.error("失败");
        }
        String[] menu=menuids.split(",");

        sysRolePermissionService.insert(id,menu);
        return AjaxResult.success("操作成功");
    }
}
