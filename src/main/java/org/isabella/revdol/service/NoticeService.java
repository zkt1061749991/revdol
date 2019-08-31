package org.isabella.revdol.service;

import org.isabella.revdol.domin.Event;
import org.isabella.revdol.domin.Feedback;
import org.isabella.revdol.domin.Notice;

import java.util.List;

public interface NoticeService {
    List<Notice> getNoticeList();
    Notice getNotice(String id);
    void insertFeedback(Feedback feedback);
    List<Event> getNowIndexBanner();
}
