package org.isabella.revdol.controller;

import org.isabella.revdol.domin.*;
import org.isabella.revdol.service.XcxApiService;
import org.isabella.revdol.service.impl.AccountServiceImpl;
import org.isabella.revdol.service.impl.NoticeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class NoticeController {
    @Autowired
    private NoticeServiceImpl noticeService;
    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping("notice/notice")
    public String viewNotices(Model model, HttpSession session) {
        List<Notice> noticeList = noticeService.getNoticeList();
        model.addAttribute("noticeList",noticeList);
        return "notice/notice";
    }

    @GetMapping("notice/detail")
    public String viewNotice(@RequestParam("id") String id,Model model) {
        if(id != null) {
            Notice notice = noticeService.getNotice(id);
            model.addAttribute("notice",notice);
        }
        return  "notice/detail";
    }

    @GetMapping("notice/talk")
    public String viewTalk(HttpSession session) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            return "notice/talk";
        } else
            return "redirect:/";
    }

    @PostMapping("notice/talk")
    public String insertFeedback(@RequestParam("type") String type,@RequestParam("body") String body,HttpSession session,Model model) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            Feedback feedback = new Feedback();
            feedback.setQq(qq);
            feedback.setBody(body);
            feedback.setType(type);
            noticeService.insertFeedback(feedback);
            Account account = accountService.getAccount(qq);
            List<XcxData> xcxDataList = XcxApiService.getEventImgList();
            BData bgf = XcxApiService.getBFansNum("364225566");
            BData bhyh = XcxApiService.getBFansNum("2315820");
            model.addAttribute("xcxDataList",xcxDataList);
            model.addAttribute("bgf",bgf);
            model.addAttribute("bhyh",bhyh);
            model.addAttribute("account", account);
            return "account/space";
        } else
            return "redirect:/";
    }

    @GetMapping("notice/datashow")
    public String viewData(Model model,HttpSession session) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            XcxData xcxData = XcxApiService.getWeekData();
            model.addAttribute("xcxData", xcxData);
            return "notice/datashow";
        }
        else {
            return "redirect:/";
        }
    }

}
