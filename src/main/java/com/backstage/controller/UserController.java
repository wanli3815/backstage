package com.backstage.controller;

import com.backstage.aspectj.annotation.Log;
import com.backstage.aspectj.enums.BusinessType;
import com.backstage.entity.SysRole;
import com.backstage.entity.SysUser;
import com.backstage.entity.SysUserRole;
import com.backstage.service.SysRoleService;
import com.backstage.service.SysUserRoleService;
import com.backstage.service.SysUserService;
import com.backstage.unils.AjaxResult;
import com.backstage.unils.PasswordUtils;
import com.backstage.unils.StringUtils;
import com.backstage.unils.page.TableDataInfo;
import com.backstage.vo.user.UserAdd;
import com.backstage.vo.user.UserEdit;
import com.backstage.vo.user.UserRoleEdit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static org.apache.shiro.util.ThreadContext.getSubject;

/**
 * @description: 用户列表
 * @author: wanli3815@163.com
 * @create: 2020-01-14 15:18
 **/
@Controller
@Slf4j
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    private String prefix="admin/user";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;
    /**
     * 管理员列表显示
     * */
    @GetMapping("/index")
    public String index(){
        return prefix+"/index";
    }
    /**
     * 管理员列表数据
     * */
    @RequiresPermissions("system:user:list")
    @GetMapping("/userList")
    @ResponseBody
    public TableDataInfo list(SysUser user){
        log.info("记录开始时间:{}",new Date());
        startPage();
        List<SysUser> all = sysUserService.findList(user);
        log.info("记录结束时间:{}",new Date());
        return getDataTable(all);
    }
    /**
     * 管理员新增显示
     * */
    @GetMapping("/add")
    public String add(){
        return  prefix+"/add";
    }

    /**
     *管理员新增提交
     */
    @Log(title = "用户管理",busionesstype = BusinessType.INSERT)
    @RequiresPermissions("system:user:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addPost(@Valid UserAdd user, BindingResult result){
        if(result.hasErrors()){
            FieldError error = result.getFieldErrors().get(0);
            return AjaxResult.error(error.getDefaultMessage());
        }
        int userExist = sysUserService.userExist(user.getUsername());
        if(userExist>0){
            return AjaxResult.error("该用户已存在");
        }
        int insert = sysUserService.insert(user);
        if(insert>0){
            return AjaxResult.success("新增成功");
        }
        return AjaxResult.error("新增失败");
    }
    //编辑管理员
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") String id, ModelMap map){
        SysUser userInfo = sysUserService.userInfo(id);
        map.put("user",userInfo);
        return  prefix+"/edit";
    }
    //提交用户编辑后的信息
    @Log(title = "用户管理",busionesstype = BusinessType.UPDATE)
    @RequiresPermissions("system:user:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editPost(@Valid UserEdit userEdit,BindingResult result){
        if(result.hasErrors()){
            FieldError error = result.getFieldErrors().get(0);
            return AjaxResult.error(error.getDefaultMessage());
        }
        int update = sysUserService.update(userEdit);
        if (update>0) {
            return AjaxResult.success("修改成功");
        }


        return AjaxResult.error("修改失败");
    }

    @RequiresPermissions("system:user:del")
    @DeleteMapping("/del")
    @ResponseBody
    @Log(title = "用户管理",busionesstype = BusinessType.DELETE)
    public  AjaxResult delUser(@RequestParam("id") String id){
        if (StringUtils.isEmpty(id)) {
            return AjaxResult.error("信息不完整");
        }
        boolean delUser = sysUserService.delUser(id);
        if(delUser){
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }

    @GetMapping("/userauth/{id}")
    public String userauth(@PathVariable("id") String id,ModelMap mmp){
        List<SysRole> all = sysRoleService.findAll();
        List<String> roleByUser = sysUserRoleService.getRoleByUser(id);
        if(StringUtils.isNotNull(roleByUser)){
            mmp.put("userrole",roleByUser);
        }
        mmp.put("id",id);
        mmp.put("role",all);

        return  prefix+"/userauth";
    }
    @Log(title = "用户管理",busionesstype = BusinessType.AUTH)
    @RequiresPermissions("system:user:auth")
    @PostMapping("/userauth")
    @ResponseBody
    public AjaxResult userauthPost(UserRoleEdit userRoleEdit,BindingResult result){
        if(result.hasErrors()){
            FieldError error = result.getFieldErrors().get(0);
            return  AjaxResult.error(error.getDefaultMessage());
        }
        int i=sysUserRoleService.insert(userRoleEdit);
        if(i>0){
            return AjaxResult.success("编辑成功");
        }
        return  AjaxResult.error("编辑失败");
    }

    @GetMapping("/changepwd")
    public String changepwd(){
        return prefix+"/changepwd";
    }
    @PostMapping("/changepwd")
    @ResponseBody
    public AjaxResult changepwd(@RequestParam("oldpwd")String oldpwd,@RequestParam("newpwd")String newpwd){
        SysUser user=new SysUser();
        Object obj = getSubject().getPrincipal();
        if (StringUtils.isNotNull(obj))
        {
            BeanUtils.copyProperties(obj, user);
            //检查原密码是否一致
            String salt = user.getSalt();
            String password = user.getPassword();
            boolean matches = PasswordUtils.matches(salt, oldpwd, password);
            if(!matches){
                return  AjaxResult.error("原密码输入不正确");
            }
            user.setPassword(PasswordUtils.encode(newpwd,salt));
            int i = sysUserService.changePwd(user);
            if(i>0){
                return  AjaxResult.success("修改成功");
            }

        }
        return  AjaxResult.error("修改失败");
    }
    @PostMapping("/logout")
    @ResponseBody
    public AjaxResult logout(){
        getSubject().logout();
        return AjaxResult.success("退出成功");
    }

}
