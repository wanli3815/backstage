package com.backstage.service.impl;

import com.backstage.dao.SysPermissionMapper;
import com.backstage.entity.SysPermission;
import com.backstage.service.SysPermissionService;
import com.backstage.unils.IdWorker;
import com.backstage.unils.text.Convert;
import com.backstage.vo.permission.Category;
import com.backstage.vo.permission.PermissionAdd;
import com.backstage.vo.permission.PermissionEdit;
import com.backstage.vo.user.Menu;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-16 10:13
 **/
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Override
    public List<SysPermission> findAll() {
        Example example =new Example(SysPermission.class);
        example.setOrderByClause("order_num asc");
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("deleted","1");

        List<SysPermission> sysPermissions = sysPermissionMapper.selectByExample(example);
        return sysPermissions;
    }

    @Override
    public List<Category> getAll() {
        Example example =new Example(SysPermission.class);
        example.setOrderByClause("order_num asc");
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("deleted","1");
        criteria.andNotEqualTo("type","3");
        List<SysPermission> all = sysPermissionMapper.selectByExample(example);

        List<Category> categorylist=new ArrayList<>();
        for (SysPermission permission : all) {
            if(permission.getPid().equals("0")){
                Category category=new Category();
                BeanUtils.copyProperties(permission,category);
                categorylist.add(category);
            }

        }
        categorylist.sort(Comparator.comparing(Category::getOrderNum));
        findSubCategory(categorylist,all);
        return categorylist;
    }

    @Override
    public int insert(PermissionAdd permissionAdd) {
        SysPermission sysPermission=new SysPermission();
        BeanUtils.copyProperties(permissionAdd,sysPermission);
        sysPermission.setId(Convert.toStr(new IdWorker().nextId()));
        sysPermission.setCreateTime(new Date());
        sysPermission.setUpdateTime(new Date());
        return sysPermissionMapper.insertSelective(sysPermission);
    }

    @Override
    public SysPermission getInfo(String id) {
        SysPermission sysPermission=new SysPermission();
        sysPermission.setId(id);
        return sysPermissionMapper.selectOne(sysPermission);
    }

    @Override
    public int update(PermissionEdit permissionEdit) {
        SysPermission sysPermission=new SysPermission();
        BeanUtils.copyProperties(permissionEdit,sysPermission);
        sysPermission.setUpdateTime(new Date());
        return sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
    }

    @Override
    public boolean isFindSub(String id) {
        SysPermission sysPermission=new SysPermission();
        sysPermission.setPid(id);
        int i = sysPermissionMapper.selectCount(sysPermission);

        return i>0?true:false;
    }

    @Override
    public int delete(String id) {
        SysPermission sysPermission=new SysPermission();
        sysPermission.setId(id);
        int delete = sysPermissionMapper.delete(sysPermission);
        return delete;
    }

    @Override
    public List<Menu> permissionTreeList(List<String> permissionId) {
        Example example =new Example(SysPermission.class);
        example.setOrderByClause("order_num asc");
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("deleted","1");
        criteria.andNotEqualTo("type","3");
        criteria.andIn("id",permissionId);
        List<SysPermission> all = sysPermissionMapper.selectByExample(example);

        List<Menu> list=new ArrayList<>();
        if (all==null||all.isEmpty()){
            return list;
        }
        for(SysPermission sysPermission:all){
            if(sysPermission.getPid().equals("0")){
                Menu menu=new Menu();
                BeanUtils.copyProperties(sysPermission,menu);

                menu.setChildren(getChildExcBtn(sysPermission.getId(),all));

                list.add(menu);
            }
        }
        return list;
    }

    @Override
    public Set<String> permissionPerms(List<String> permissionId) {
        Example example =new Example(SysPermission.class);
        example.setOrderByClause("order_num asc");
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("deleted","1");
        criteria.andEqualTo("type","3");
        criteria.andIn("id",permissionId);
        List<SysPermission> all = sysPermissionMapper.selectByExample(example);
        Set<String> roleper=new HashSet<>();
        for (SysPermission permission : all) {
            roleper.add(permission.getPerms());
        }
        return roleper;
    }

    private List<Menu>getChildExcBtn(String id,List<SysPermission> all){

        List<Menu> list=new ArrayList<>();
        for(SysPermission sysPermission:all){
            if(sysPermission.getPid().equals(id)&&sysPermission.getType()!=3){
                Menu menu=new Menu();
                BeanUtils.copyProperties(sysPermission,menu);
                menu.setChildren(getChildExcBtn(sysPermission.getId(),all));
                list.add(menu);
            }
        }
        return list;
    }

    private void findSubCategory(List<Category> categorylist, List<SysPermission> all) {
        for (Category category : categorylist) {
            List<Category> subCatrgory =new ArrayList<>();

            for (SysPermission sysPermission : all) {
                if(category.getId().equals(sysPermission.getPid())){
                    Category categories=new Category();
                    BeanUtils.copyProperties(sysPermission,categories);
                    subCatrgory.add(categories);
                }
                //进行排序
                subCatrgory.sort(Comparator.comparing(Category::getOrderNum));
                category.setSubcategory(subCatrgory);
                findSubCategory(subCatrgory, all);
            }

        }
    }
}
