package com.backstage.controller;

import com.backstage.aspectj.annotation.Log;
import com.backstage.aspectj.enums.BusinessType;
import com.backstage.entity.Notice;
import com.backstage.service.NoticeService;
import com.backstage.unils.AjaxResult;
import com.backstage.unils.page.TableDataInfo;
import com.backstage.vo.notice.NoticeSearch;
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
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-20 11:55
 **/
@Controller
@RequestMapping("/admin/notice")
public class NoticeController extends BaseController {

    private String prefix = "admin/notice";

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/index")
    public String index() {
        return prefix + "/index";
    }

    @Log(title = "通知管理",busionesstype = BusinessType.LIST)
    @RequiresPermissions("news:notice:list")
    @GetMapping("/getList")
    @ResponseBody
    public TableDataInfo list(NoticeSearch noticesearch) {
        startPage();
        List<Notice> all = noticeService.findAll(noticesearch);
        return getDataTable(all);
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @Log(title = "通知管理",busionesstype = BusinessType.INSERT)
    @RequiresPermissions("news:notice:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(@Valid Notice notice, BindingResult result) {
        if (result.hasErrors()) {
            FieldError error = result.getFieldErrors().get(0);
            return AjaxResult.error(error.getDefaultMessage());
        }
        int insert = noticeService.insert(notice);
        if(insert>0){
            return AjaxResult.success("添加成功");
        }
        return AjaxResult.error("失败");
    }
    @RequiresPermissions("news:notice:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmp){

        Notice notice = noticeService.getNotice(id);
        mmp.put("news",notice);
        return prefix + "/edit";
    }
    @Log(title = "通知管理",busionesstype = BusinessType.UPDATE)
    @RequiresPermissions("news:notice:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(@Valid Notice notice, BindingResult result) {
        if (result.hasErrors()) {
            FieldError error = result.getFieldErrors().get(0);
            return AjaxResult.error(error.getDefaultMessage());
        }
        int insert = noticeService.update(notice);
        if(insert>0){
            return AjaxResult.success("修改成功");
        }
        return AjaxResult.error("失败");
    }
    @Log(title = "通知管理",busionesstype = BusinessType.DELETE)
    @RequiresPermissions("news:notice:del")
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public AjaxResult del(@PathVariable("id") String id){
        int delete = noticeService.delete(id);
        if(delete>0){
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.success("删除失败");
    }
}
