package org.isabella.revdol.controller;


import org.isabella.revdol.domin.Account;
import org.isabella.revdol.domin.Pointlog;
import org.isabella.revdol.service.impl.AccountServiceImpl;
import org.isabella.revdol.service.impl.PointServiceImpl;
import org.isabella.revdol.service.impl.SignonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PointController {
    @Autowired
    private SignonServiceImpl signonService;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PointServiceImpl pointService;

    @GetMapping("point/point")
    public String viewPoint(Model model, HttpSession session) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            Account account = accountService.getAccount(qq);
            model.addAttribute("account", account);
            return "point/point";
        } else
            return "redirect:/";
    }

    @GetMapping("point/explain")
    public String viewExplain() {
        return "point/explain";
    }

    @GetMapping("point/details")
    public String viewDetails(Model model, HttpSession session) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            List<Pointlog> pointlogList = pointService.getPointlogListByQq(qq);
            model.addAttribute("pointlogList", pointlogList);
            return "point/details";
        } else
            return "redirect:/";
    }

    @GetMapping("point/show_pic")
    public String viewpic(Model model, HttpSession session, @RequestParam("name") String name) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            model.addAttribute("name", name);
            return "point/show_pic";
        } else
            return "redirect:/";
    }

}
