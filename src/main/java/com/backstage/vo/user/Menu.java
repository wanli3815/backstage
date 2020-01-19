package com.backstage.vo.user;

import com.backstage.vo.permission.Category;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-17 14:39
 **/
@Data
public class Menu {
    private String id;

    private String name;

    private String pid;

    private String url;

    private Integer orderNum;

    private List<?> children;
}
