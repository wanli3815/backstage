package com.backstage.service.impl;

import com.backstage.dao.SysUserMapper;
import com.backstage.entity.SysUser;
import com.backstage.exception.login.UserNotExistsException;
import com.backstage.service.SysUserRoleService;
import com.backstage.service.SysUserService;
import com.backstage.unils.IdWorker;
import com.backstage.unils.PasswordUtils;
import com.backstage.unils.ServletUtils;
import com.backstage.unils.StringUtils;
import com.backstage.unils.text.Convert;
import com.backstage.vo.user.UserAdd;
import com.backstage.vo.user.UserEdit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-13 17:20
 **/
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Override
    public SysUser login(String userName, String passWord) {
        SysUser user=new SysUser();
        user.setUsername(userName);
        SysUser userinfo= sysUserMapper.selectOne(user);
        if(userinfo==null){
            //用户名不存在
            throw new UserNotExistsException();
        }
        //如果密码不一致则提示密码错误
        if (!PasswordUtils.matches(userinfo.getSalt(),passWord,userinfo.getPassword())) {

        }
        return userinfo;
    }

    @Override
    public List<SysUser> findList(SysUser user) {

        List<SysUser> sysUsers=null;
        if(StringUtils.isNotNull(user.getUsername())){
            Example example=new Example(SysUser.class);
            Example.Criteria criteria=example.createCriteria();
            criteria.andLike("username", "%" + user.getUsername()+"%");
            sysUsers = sysUserMapper.selectByExample(example);
        }else{
            sysUsers = sysUserMapper.selectAll();
        }
        return sysUsers;
    }



    @Override
    public int insert(UserAdd user) {
        SysUser newuser=new SysUser();
        BeanUtils.copyProperties(user,newuser);
        Date date=new Date();
        newuser.setCreateTime(date);
        newuser.setUpdateTime(date);
        newuser.setSalt(PasswordUtils.getSalt());
        newuser.setPassword(PasswordUtils.encode(user.getPassword(),newuser.getSalt()));
        newuser.setId(Convert.toStr(new IdWorker().nextId()));
        return  sysUserMapper.insertSelective(newuser);
    }


    /**
     * 判断用户名是否存在
     * @param UserName
     * @return
     */
    @Override
    public int userExist(String userName) {
        SysUser user = new SysUser();
        user.setUsername(userName);
        return  sysUserMapper.selectCount(user);
    }

    /**
     * 根据用户信息获取完整数据库信息
     * @param userId
     * @return
     */
    @Override
    public SysUser userInfo(String userId) {
        Example example=new Example(SysUser.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("id",userId);
        SysUser user = sysUserMapper.selectOneByExample(example);


        return user;
    }

    /**
     * 提交编辑的用户信息
     * @param userEdit
     * @return
     */
    @Override
    public int update(UserEdit userEdit) {
        SysUser newuser=new SysUser();
        BeanUtils.copyProperties(userEdit,newuser);
        Date date=new Date();
        newuser.setUpdateTime(date);
        int i = sysUserMapper.updateByPrimaryKeySelective(newuser);
        return i;
    }

    @Override
    public int changePwd(SysUser user) {
        Date date=new Date();
        user.setUpdateTime(date);
        int i = sysUserMapper.updateByPrimaryKeySelective(user);
        return i;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delUser(String id) {
        SysUser newuser=new SysUser();
        newuser.setId(id);

        boolean exists = sysUserMapper.existsWithPrimaryKey(newuser);
        if(exists){
            int delete = sysUserMapper.delete(newuser);
            sysUserRoleService.delRoleByUser(id);
            return delete>0?true:false;
        }
        return false;
    }
}
