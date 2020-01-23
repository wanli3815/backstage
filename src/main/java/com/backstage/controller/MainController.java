package com.backstage.controller;

import com.backstage.entity.SysUser;
import com.backstage.service.SysPermissionService;
import com.backstage.service.SysRolePermissionService;
import com.backstage.service.SysUserRoleService;
import com.backstage.unils.AjaxResult;
import com.backstage.unils.StringUtils;
import com.backstage.vo.user.Menu;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.apache.shiro.util.ThreadContext.getSubject;

/**
 * @description: 后台主页面
 * @author: wanli3815@163.com
 * @create: 2020-01-14 14:36
 **/
@Controller
@RequestMapping("/admin/main")
@Slf4j
public class MainController {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysPermissionService sysPermissionService;

    private String prefix="admin/main";
    @GetMapping("/index")
    public String main(){
        return prefix+"/index";
    }
    @GetMapping("/welcome")
    public  String Welcome(){
        return prefix+"/welcome";
    }
    @GetMapping("/getmenu")
    @ResponseBody
    public AjaxResult getmenu(){
        SysUser user=new SysUser();
        Object obj = getSubject().getPrincipal();
        if (StringUtils.isNotNull(obj))
        {
            user = new SysUser();
            BeanUtils.copyProperties(obj, user);
        }
        String userid=user.getId();
        List<String> roleByUser = sysUserRoleService.getRoleByUser(userid);
        List<String> permissionId = sysRolePermissionService.getRoleByMenuPermission(roleByUser);
        List<Menu> menus = sysPermissionService.permissionTreeList(permissionId);

        return AjaxResult.success("",menus);
    }
}
