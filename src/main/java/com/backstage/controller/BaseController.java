package com.backstage.controller;

import com.backstage.unils.AjaxResult;
import com.backstage.unils.IdWorker;
import com.backstage.unils.ResultEnum;
import com.backstage.unils.StringUtils;
import com.backstage.unils.page.PageDomain;
import com.backstage.unils.page.TableDataInfo;
import com.backstage.unils.page.TableSupport;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 基础控制器
 * @author: wanli3815@163.com
 * @create: 2020-01-14 16:20
 **/
@Controller
public class BaseController  {
    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            //String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        int size = list.size();
        if(size==0){
            rspData.setMsg("暂无数据信息");
            rspData.setCode(ResultEnum.ERROR.getCode());
        }else{
            rspData.setCode(ResultEnum.SUCCESS.getCode());
            rspData.setData(list);
            rspData.setCount(new PageInfo(list).getTotal());
        }
        return rspData;
    }


    @Value(value = "${backstage.uploadpath}")
    private String uploadPath;

    @PostMapping("/common/upload")
    @ResponseBody
    public AjaxResult upload(HttpServletRequest req, @RequestParam("file") MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = new IdWorker().nextId() +suffixName;
        //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
        String destFileName = uploadPath+fileName;
        //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
        File destFile = new File(destFileName);
        destFile.getParentFile().mkdirs();
        //5.把浏览器上传的文件复制到希望的位置
        file.transferTo(destFile);
        Map<String,String> map=new HashMap<>();
        map.put("src","/uploads/"+fileName);
        return  new AjaxResult(0,"成功",map);
    }
}
