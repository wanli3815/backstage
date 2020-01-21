package com.backstage.controller;

import com.backstage.aspectj.annotation.Log;
import com.backstage.aspectj.enums.BusinessType;
import com.backstage.entity.SysPermission;
import com.backstage.service.SysPermissionService;
import com.backstage.service.SysRolePermissionService;
import com.backstage.unils.AjaxResult;
import com.backstage.unils.StringUtils;
import com.backstage.vo.permission.Category;
import com.backstage.vo.permission.PermissionAdd;
import com.backstage.vo.permission.PermissionEdit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: 权限控制器
 * @author: wanli3815@163.com
 * @create: 2020-01-16 10:08
 **/
@Controller
@RequestMapping("/admin/permission")
public class PermissionController extends BaseController {

    private String prefix="admin/permission";

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @GetMapping("/index")
    public String index(){
        return prefix+"/index";
    }

    @Log(title = "权限管理",busionesstype = BusinessType.LIST)
    @RequiresPermissions("system:permission:list")
    @GetMapping("/getList")
    @ResponseBody
    public AjaxResult list(){
        List<SysPermission> all = sysPermissionService.findAll();
        return AjaxResult.success("成功",all);
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") String id, ModelMap mmp){
        List<Category> all = sysPermissionService.getAll();
        mmp.put("category",all);
        mmp.put("pid",id);
        return prefix+"/add";
    }
    @Log(title = "权限管理",busionesstype = BusinessType.INSERT)
    @RequiresPermissions("system:permission:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addPost(@Valid PermissionAdd permissionAdd,BindingResult result){
        if(result.hasErrors()){
            FieldError error = result.getFieldErrors().get(0);
            return AjaxResult.error(error.getDefaultMessage());
        }
        if(permissionAdd.getType().equals(1)){
            permissionAdd.setCode("");
            permissionAdd.setOrderNum(0);
            permissionAdd.setUrl("");
            permissionAdd.setPerms("");
       }
        if(permissionAdd.getType().equals(2)){
            permissionAdd.setPerms("");

            if(StringUtils.isEmpty(permissionAdd.getUrl())){
                return AjaxResult.error("请输入Url");
            }
            if(!StringUtils.isNotNull(permissionAdd.getOrderNum())){
                return AjaxResult.error("请输入排序");
            }

        }
        if(permissionAdd.getType().equals(3)){
            permissionAdd.setCode("");
            permissionAdd.setOrderNum(0);
            permissionAdd.setUrl("");
            if(StringUtils.isEmpty(permissionAdd.getPerms())){
                return AjaxResult.error("请输入授权码");
            }
        }
        int insert=sysPermissionService.insert(permissionAdd);
        if(insert>0){
            return AjaxResult.success("操作成功");
        }
        return AjaxResult.error("操作失败");
    }
    @GetMapping("/edit/{id}/{pid}")
    public String edit(@PathVariable("id") String id,@PathVariable("pid") String pid,ModelMap mmp){
        List<Category> all = sysPermissionService.getAll();
        SysPermission sysPermission=sysPermissionService.getInfo(id);
        mmp.put("category",all);
        mmp.put("pid",pid);
        mmp.put("permission",sysPermission);

        return prefix+"/edit";
    }
    @Log(title = "权限管理",busionesstype = BusinessType.UPDATE)
    @RequiresPermissions("system:permission:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editPost(@Valid PermissionEdit permissionEdit, BindingResult result){
        if(result.hasErrors()){
            FieldError error = result.getFieldErrors().get(0);
            return AjaxResult.error(error.getDefaultMessage());
        }
        if(permissionEdit.getType().equals(1)){
            permissionEdit.setCode("");
            permissionEdit.setOrderNum(0);
            permissionEdit.setPerms("");
            permissionEdit.setUrl("");

        }
        if(permissionEdit.getType().equals(2)){

            permissionEdit.setPerms("");

           /* if(StringUtils.isEmpty(permissionEdit.getCode())){
                return AjaxResult.error("请输入图标");
            }*/
            if(StringUtils.isEmpty(permissionEdit.getUrl())){
                return AjaxResult.error("请输入Url");
            }
            if(!StringUtils.isNotNull(permissionEdit.getOrderNum())){
                return AjaxResult.error("请输入排序");
            }

        }
        if(permissionEdit.getType().equals(3)){
            permissionEdit.setCode("");
            permissionEdit.setOrderNum(0);
            permissionEdit.setUrl("");
            if(StringUtils.isEmpty(permissionEdit.getPerms())){
                return AjaxResult.error("请输入授权码");
            }
        }
        int update=sysPermissionService.update(permissionEdit);
        if(update>0){
            return AjaxResult.success("操作成功");
        }
        return AjaxResult.error("操作失败");
    }
    @Log(title = "权限管理",busionesstype = BusinessType.DELETE)
    @RequiresPermissions("system:permission:del")
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") String id){
        boolean isfindsub=sysPermissionService.isFindSub(id);
        if(isfindsub){
            return AjaxResult.error("请先删除其下方的菜单");
        }
        boolean findPer = sysRolePermissionService.isFindPer(id);
        if(findPer){
            return AjaxResult.error("请先删除角色里的引用权限");
        }
        int i=sysPermissionService.delete(id);
        if(i>0){
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("刪除失敗");
    }
}
