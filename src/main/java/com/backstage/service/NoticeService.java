package com.backstage.service;

import com.backstage.entity.Notice;
import com.backstage.vo.notice.NoticeSearch;

import java.util.List;

/**
 * @description:
 * @author: wanli3815@163.com
 * @create: 2020-01-20 11:49
 **/
public interface NoticeService {

    int insert(Notice notice);

    int update(Notice notice);

    int delete(String id);

    Notice getNotice(String id);

    List<Notice> findAll(NoticeSearch noticeSearch);
}
