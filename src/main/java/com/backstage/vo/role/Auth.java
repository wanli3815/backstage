package com.backstage.vo.role;

import com.backstage.entity.SysPermission;
import com.backstage.vo.permission.Category;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-16 16:22
 **/
@Data
public class Auth {
    private List<SysPermission> list;
    private List<?> checked;
}
