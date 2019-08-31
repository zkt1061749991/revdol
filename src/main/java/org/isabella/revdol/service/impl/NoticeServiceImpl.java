package org.isabella.revdol.service.impl;

import org.isabella.revdol.domin.Event;
import org.isabella.revdol.domin.Feedback;
import org.isabella.revdol.domin.Notice;
import org.isabella.revdol.persistence.NoticeMapper;
import org.isabella.revdol.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> getNoticeList() {
        List<Notice> list = noticeMapper.getNoticeList();
        for(Notice notice : list) {
            notice.makeshow();
        }
        return list;
    }

    @Override
    public Notice getNotice(String id) {
        return noticeMapper.getNotice(id);
    }

    @Override
    public void insertFeedback(Feedback feedback) {
        noticeMapper.insertFeedback(feedback);
    }

    @Override
    public List<Event> getNowIndexBanner() {
        return noticeMapper.getNowIndexBanner();
    }
}
