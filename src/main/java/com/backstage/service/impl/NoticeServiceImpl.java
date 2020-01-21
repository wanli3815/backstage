package com.backstage.service.impl;

import com.backstage.dao.NoticeMapper;
import com.backstage.entity.Notice;
import com.backstage.service.NoticeService;
import com.backstage.unils.IdWorker;
import com.backstage.unils.ServletUtils;
import com.backstage.unils.StringUtils;
import com.backstage.unils.text.Convert;
import com.backstage.vo.notice.NoticeSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-20 11:50
 **/
@Service
public class NoticeServiceImpl implements NoticeService {


    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public int insert(Notice notice) {
        notice.setId(Convert.toStr(new IdWorker().nextId()));
        notice.setCreateTime(new Date());
        notice.setUpdateTime(new Date());
        return noticeMapper.insertSelective(notice);

    }

    @Override
    public int update(Notice notice) {
        notice.setUpdateTime(new Date());

        return noticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public int delete(String id) {

        List<String> idlist=new ArrayList<>();
        if(id.contains(",")){
            String[] split = id.split(",");
            for (String s : split) {
                idlist.add(s);
            }
        }else{
            idlist.add(id);
        }
        Example example=new Example(Notice.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("id",idlist);
        return noticeMapper.deleteByExample(example);
    }

    @Override
    public Notice getNotice(String id) {
        Notice notice=new Notice();
        notice.setId(id);

        return noticeMapper.selectByPrimaryKey(notice);
    }

    @Override
    public List<Notice> findAll(NoticeSearch search) {
        Example example=new Example(Notice.class);
        Example.Criteria criteria=example.createCriteria();
        if(StringUtils.isNotEmpty(search.getTitle())){
            criteria.andLike("title","%"+search.getTitle()+"%");
        }
        if(StringUtils.isNotEmpty(search.getAuthor())){
            criteria.andLike("author","%"+search.getAuthor()+"%");
        }
        String start = ServletUtils.getParameter("start");
        String end = ServletUtils.getParameter("end");
        if(StringUtils.isNotEmpty(start)){
            criteria.andGreaterThanOrEqualTo("createTime",start+" 00:00:00");
        }
        if(StringUtils.isNotEmpty(end)){
            criteria.andLessThanOrEqualTo("createTime",end+" 23:59:59");
        }
        List<Notice> notices = noticeMapper.selectByExample(example);
        return notices;
    }
}
