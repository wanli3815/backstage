package com.backstage.vo.permission;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

/**
 * @description:树形菜单
 * @author: wanli3815@163.com
 * @create: 2020-01-16 11:19
 **/
@Data
public class Category {
    private String id;

    private String name;

    private String pid;

    @Column(name = "order_num")
    private Integer orderNum;

    private List<Category> subcategory;
}
