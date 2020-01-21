package com.backstage.service.impl;

import com.backstage.dao.SysUserRoleMapper;
import com.backstage.entity.SysUserRole;
import com.backstage.service.SysUserRoleService;
import com.backstage.unils.IdWorker;
import com.backstage.unils.StringUtils;
import com.backstage.unils.text.Convert;
import com.backstage.vo.user.UserRoleEdit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-17 10:19
 **/
@Transactional
@Service
@Slf4j
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<String> getRoleByUser(String userId) {
        Example example=new Example(SysUserRole.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("userId",userId);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectByExample(example);
        List<String> roleList=new ArrayList<>();
        for (SysUserRole userRole : userRoles) {
            roleList.add(userRole.getRoleId());
        }
        return roleList;
    }

    @Override
    public int insert(UserRoleEdit userRoleEdit) {
        if(!StringUtils.isNotNull(userRoleEdit.getRoleIds())){
            return 0;
        }

        List<SysUserRole> userRoles=new ArrayList<>();
        Date createTime=new Date();
        for (String roleId : userRoleEdit.getRoleIds()) {
            if(StringUtils.isNotNull(roleId)){
                log.info("角色id：{}",roleId);
                SysUserRole sysUserRole=new SysUserRole();
                sysUserRole.setRoleId(roleId);
                sysUserRole.setCreateTime(createTime);
                sysUserRole.setId(Convert.toStr(new IdWorker().nextId()));
                sysUserRole.setUserId(userRoleEdit.getUserId());
                userRoles.add(sysUserRole);
            }

        }

        /*
        * 删除以前的数据
        * */
        Example example=new Example(SysUserRole.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("userId",userRoleEdit.getUserId());
        sysUserRoleMapper.deleteByExample(example);
        //批量添加


        int i = sysUserRoleMapper.insertUserRole(userRoles);
        return i;
    }

    @Override
    public void delRoleByUser(String userId) {
        Example example=new Example(SysUserRole.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("userId",userId);
        sysUserRoleMapper.deleteByExample(example);
    }

    @Override
    public boolean isUserCountByRole(String roleId) {
        Example example=new Example(SysUserRole.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("roleId",roleId);
        int i = sysUserRoleMapper.selectCountByExample(example);
        return i>0?true:false;
    }


}
