package org.isabella.revdol.controller;


import org.isabella.revdol.domin.*;
import org.isabella.revdol.service.XcxApiService;
import org.isabella.revdol.service.impl.AccountServiceImpl;
import org.isabella.revdol.service.impl.NoticeServiceImpl;
import org.isabella.revdol.service.impl.SignonServiceImpl;
import org.isabella.revdol.util.RandomValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private SignonServiceImpl signonService;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private NoticeServiceImpl noticeService;

    @GetMapping("account/login")
    public String viewSignon() {
        return "redirect:/";
    }

    //登录成功跳转至积分查询页面
    @PostMapping("account/login")
    public String login(@RequestParam("qq") String qq, @RequestParam("password") String password
            , Model model, HttpSession session) {
        Signon signon = signonService.getSignon(qq, password);
        Account account = accountService.getAccount(qq);
        if (signon != null && account != null) {
//            List<XcxData> xcxDataList = XcxApiService.getEventImgList();
//            BData bgf = XcxApiService.getBFansNum("364225566");
//            BData bhyh = XcxApiService.getBFansNum("2315820");
            session.setAttribute("qq", qq);
//            System.out.println(account.getPoint());
//            model.addAttribute("account", account);
//            model.addAttribute("xcxDataList",xcxDataList);
//            model.addAttribute("bgf",bgf);
//            model.addAttribute("bhyh",bhyh);
            return "redirect:space";
        } else {
            String errorString = "未注册/QQ号码或查询密码错误，请重新输入";
            model.addAttribute("errorString", errorString);
            return "index.html";
        }
    }

//用于上线测试，暂停登录的控制器
//    @PostMapping("account/login")
//    public String login(@RequestParam("qq") String qq, @RequestParam("password") String password
//            , Model model, HttpSession session) {
//        if(qq.equals("1") || qq.equals("2") || qq.equals("3") ||qq.equals("4") || qq.equals("5") ||qq.equals("6") || qq.equals("7")) {
//
//
//        Signon signon = signonService.getSignon(qq, password);
//        Account account = accountService.getAccount(qq);
//        if (signon != null && account != null) {
//            List<XcxData> xcxDataList = XcxApiService.getEventImgList();
//            BData bgf = XcxApiService.getBFansNum("364225566");
//            BData bhyh = XcxApiService.getBFansNum("2315820");
//            session.setAttribute("qq", qq);
//            System.out.println(account.getPoint());
//            model.addAttribute("account", account);
//            model.addAttribute("xcxDataList",xcxDataList);
//            model.addAttribute("bgf",bgf);
//            model.addAttribute("bhyh",bhyh);
//            return "account/space";
//        } else {
//            String errorString = "未注册/QQ号码或查询密码错误，请重新输入";
//            model.addAttribute("errorString", errorString);
//            return "index.html";
//        }
//        }
//        else {
//            return "index.html";
//        }
//    }

    @GetMapping("account/space")
    public String viewPoint(Model model, HttpSession session) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            Account account = accountService.getAccount(qq);
            //System.out.println(account.toString());
            List<XcxData> xcxDataList = XcxApiService.getEventImgList();
            BData bgf = XcxApiService.getBFansNum("364225566");
            BData bhyh = XcxApiService.getBFansNum("2315820");
            List<Event> bannerList = noticeService.getNowIndexBanner();
            model.addAttribute("xcxDataList",xcxDataList);
            model.addAttribute("bgf",bgf);
            model.addAttribute("bhyh",bhyh);
            model.addAttribute("bannerList",bannerList);
            model.addAttribute("account", account);
            return "account/space";
        } else
            return "redirect:/";
    }

    @GetMapping("account/info")
    public String viewinfo(HttpSession session, Model model) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            Account account = accountService.getAccount(qq);
            model.addAttribute("account", account);
            return "account/myinfo";
        } else
            return "redirect:/";
    }

//    @PostMapping("account/info")
//    public String infoput(@RequestParam("xcxname") String xcxname, @RequestParam("xcxuid") String xcxuid, @RequestParam("bilibili") String bilibili, Model model, HttpSession session) {
//        String qq = (String) session.getAttribute("qq");
//        if (qq != null) {
//            Account account = accountService.getAccount(qq);
//            account.setXcxuid(xcxuid);
//            account.setXcxname(xcxname);
//            account.setBilibili(bilibili);
//            accountService.updateAccount(account);
//            model.addAttribute("account", accountService.getAccount(qq));
//            return "point/point";
//        }
//        return "没有权限！";
//    }

    @PostMapping("account/info")
    public String infoput(@RequestParam("name") String name, @RequestParam("xcxname") String xcxname, @RequestParam("xcxuid") String xcxuid, @RequestParam("bilibili") String bilibili, @RequestParam("address") String address, Model model, HttpSession session) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            Account account = accountService.getAccount(qq);
            account.setName(name);
            account.setXcxuid(xcxuid);
            account.setXcxname(xcxname);
            account.setBilibili(bilibili);
            if(!address.equals("")) {
                account.setAddress(address);
            }
            accountService.updateAccount(account);
            List<XcxData> xcxDataList = XcxApiService.getEventImgList();
            BData bgf = XcxApiService.getBFansNum("364225566");
            BData bhyh = XcxApiService.getBFansNum("2315820");
            model.addAttribute("xcxDataList",xcxDataList);
            model.addAttribute("bgf",bgf);
            model.addAttribute("bhyh",bhyh);
            model.addAttribute("account", accountService.getAccount(qq));
            return "account/space";
        }
        return "redirect:/error";
    }

    @GetMapping("account/password")
    public String viewPassword(HttpSession session, Model model) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            model.addAttribute("qq", qq);
            return "account/lock";
        } else
            return "redirect:/";
    }

    @PostMapping("account/password")
    public String pswput(@RequestParam("password") String password, Model model, HttpSession session) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            Account account = accountService.getAccount(qq);
            account.hiddenadd();
            Signon signon = new Signon();
            signon.setQq(qq);
            signon.setPassword(password);
            signonService.updateSignon(signon);
            model.addAttribute("account", account);
            List<XcxData> xcxDataList = XcxApiService.getEventImgList();
            BData bgf = XcxApiService.getBFansNum("364225566");
            BData bhyh = XcxApiService.getBFansNum("2315820");
            model.addAttribute("xcxDataList",xcxDataList);
            model.addAttribute("bgf",bgf);
            model.addAttribute("bhyh",bhyh);
            return "account/space";
        }
        return "redirect:/error";
    }

    @GetMapping("account/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("qq");
        return "redirect:/";
    }

    //生成验证码
    @RequestMapping(value = "getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            System.out.println("获取验证码失败>>>>   " + e);
        }
    }

    @GetMapping("account/newfan")
    public String viewSignonForm() {
        return "account/newfan";
    }

    @PostMapping("account/newfan")
    public String newAccount(@RequestParam("new_qq") String qq, @RequestParam("new_password") String password,@RequestParam("inputString") String inputString
            , Model model, HttpSession session) {
        try {
            //从session中获取随机数
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                //加入验证码错误的信息！
                String errorString = "请输入验证码";
                model.addAttribute("errorString", errorString);
                return "account/newfan";
            }
            if (!random.equals(inputString)) {
                String errorString = "验证码输入错误";
                model.addAttribute("errorString", errorString);
                return "account/newfan";
            }
        } catch (Exception e){
            System.out.println("获取验证码失败>>>>   " + e);
            String errorString = "获取验证码错误，请重试";
            model.addAttribute("errorString", errorString);
            return "account/newfan";
        }


        Signon signon = new Signon();

        if(!accountService.accountExist(qq)) {
            signon.setQq(qq);
            signon.setPassword(password);
            signonService.insertSignon(signon);
            String successString = "注册成功！快登录吧！";
            model.addAttribute("successString", successString);
            return "index.html";
        }
        else {
            String errorString = "该QQ号码已经注册过啦！";
            model.addAttribute("errorString", errorString);
            return "account/newfan";
        }

    }


}
