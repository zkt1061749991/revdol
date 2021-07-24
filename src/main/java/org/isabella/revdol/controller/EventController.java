package org.isabella.revdol.controller;

import org.isabella.revdol.domin.Account;
import org.isabella.revdol.domin.Pointlog;
import org.isabella.revdol.domin.Receive;
import org.isabella.revdol.service.impl.AccountServiceImpl;
import org.isabella.revdol.service.impl.EventServiceImpl;
import org.isabella.revdol.service.impl.PointServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PointServiceImpl pointService;
    @Autowired
    private EventServiceImpl eventService;

    //修改此处内容变更福利
    private Receive receive = new Receive("CK113","伊莎贝拉应援会期刊《贝化忆点点》创刊纪念福利",100);
    private Receive receive2 = new Receive("QK0418","伊莎贝拉应援会期刊《贝化忆点点》第六期福利",100);
    @GetMapping("event/codemaster")
    public ModelAndView codeTransfer(HttpSession session, Model model) {
        //开启设为true，关闭设为false
        session.setAttribute("codeflag",false);
        return new ModelAndView("redirect:/event/getpoint");
    }

    @GetMapping("event/code")
    public ModelAndView codeTransferById(HttpSession session, Model model, @RequestParam("key") String key) {
        //开启设为true，关闭设为false
        if(key.equals("QK1215")) {
            session.setAttribute("codeflag", false);
        }
        else {
            session.setAttribute("codeflag", false);
        }
        return new ModelAndView("redirect:/event/receive");
    }

    @GetMapping("event/receive")
    public String receivePoint(HttpSession session,Model model) {
        boolean flag = false;
        if(session.getAttribute("codeflag") != null)
            flag = (boolean) session.getAttribute("codeflag");
        session.removeAttribute("codeflag");

        if(flag) {
            String qq = (String) session.getAttribute("qq");
            if (qq != null) {
                receive2.setQq(qq);
                Pointlog pointlog = new Pointlog();
                pointlog.setOperated_qq_id(qq);
                pointlog.setOperate_point(receive2.getPoint());
                pointlog.setOperate_type(1);
                pointlog.setType(3);
                pointlog.setDescription(receive2.getDescription() + "——扫码");
                String back;
                back = oprateRecieve2(receive2,pointlog,model);
                if (back.equals("account/login")) {
                    back = "redirect:/account/space";
                }
                return back;
            }
            else {
                model.addAttribute("eventtitle",receive2.getDescription());
                return "event/receive";
            }
        }
        else {
            String errorString = "二维码失效或扫码失败！请重试";
            model.addAttribute("errorString", errorString);
            return "fail";
        }
    }

    @PostMapping("event/receive")
    public String receivepointpost(@RequestParam("qq") String qq, @RequestParam("inputString") String inputString, HttpSession session, Model model) {
        String errorString;

        try {
            //从session中获取随机数
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                //加入验证码错误的信息！
                errorString = "请输入验证码";
                model.addAttribute("errorString", errorString);
                model.addAttribute("eventtitle",receive2.getDescription());
                return "event/receive";
            }
            if (!random.equals(inputString)) {
                errorString = "验证码输入错误";
                model.addAttribute("errorString", errorString);
                model.addAttribute("eventtitle",receive2.getDescription());
                return "event/receive";
            }
        } catch (Exception e){
            System.out.println("获取验证码失败>>>>   " + e);
            errorString = "获取验证码错误，请重试";
            model.addAttribute("errorString", errorString);
            model.addAttribute("eventtitle",receive2.getDescription());
            return "event/receive";
        }

        Account account = accountService.getAccount(qq);
        if(account != null) {
            //先判断重复领取
            receive2.setQq(qq);
            Pointlog pointlog = new Pointlog();
            pointlog.setOperated_qq_id(qq);
            pointlog.setOperate_point(receive2.getPoint());
            pointlog.setOperate_type(1);
            pointlog.setType(3);
            pointlog.setDescription(receive2.getDescription() + "——扫码");
            return oprateRecieve2(receive2, pointlog, model);
        }
        else {
            errorString = "该QQ号码未注册，请注册后重新扫码领取";
            model.addAttribute("errorString", errorString);
            return "account/newfan";
        }
    }

    private String oprateRecieve2(Receive receive, Pointlog pointlog, Model model) {
        String errorString;
        if(eventService.getReceive(receive) != null) {
            System.out.println(receive.getQq() + "：重复领取贝化值");
            errorString = "每个账号仅限领取一次，请勿重复领取";
            model.addAttribute("errorString", errorString);
            return "fail";
        }
        else {
            try {
                eventService.insertReceive(receive);
                pointService.insertPointlog(pointlog);
                errorString = "兑换成功！请登录查收";
                model.addAttribute("errorString", errorString);
                return "account/login";
            } catch (Exception e) {
                errorString = "兑换失败！请重试或联系管理员解决";
                model.addAttribute("errorString", errorString);
                model.addAttribute("eventtitle",receive.getDescription());
                return "event/receive";
            }
        }
    }

    @GetMapping("event/getpoint")
    public String getpoint(HttpSession session,Model model) {
        boolean flag = false;
        if(session.getAttribute("codeflag") != null)
        flag = (boolean) session.getAttribute("codeflag");
        session.removeAttribute("codeflag");

        if(flag) {
            String qq = (String) session.getAttribute("qq");

            if (qq != null) {
                receive.setQq(qq);
                Pointlog pointlog = new Pointlog();
                pointlog.setOperated_qq_id(qq);
                pointlog.setOperate_point(receive.getPoint());
                pointlog.setOperate_type(1);
                pointlog.setType(3);
                pointlog.setDescription(receive.getDescription() + "——扫码");
                String back;
                back = oprateRecieve(receive,pointlog,model);
                if (back.equals("account/login")) {
                    back = "redirect:/account/space";
                }
                return back;
            }
            else {
                model.addAttribute("eventtitle",receive.getDescription());
                return "event/getpoint";
            }
        }
        else {
            String errorString = "二维码失效或扫码失败！请重试";
            model.addAttribute("errorString", errorString);
            return "fail";
        }
    }

    @PostMapping("event/getpoint")
    public String getpointpost(@RequestParam("qq") String qq, @RequestParam("inputString") String inputString, HttpSession session, Model model) {
        String errorString;

        try {
            //从session中获取随机数
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                //加入验证码错误的信息！
                errorString = "请输入验证码";
                model.addAttribute("errorString", errorString);
                model.addAttribute("eventtitle",receive.getDescription());
                return "event/getpoint";
            }
            if (!random.equals(inputString)) {
                errorString = "验证码输入错误";
                model.addAttribute("errorString", errorString);
                model.addAttribute("eventtitle",receive.getDescription());
                return "event/getpoint";
            }
        } catch (Exception e){
            System.out.println("获取验证码失败>>>>   " + e);
            errorString = "获取验证码错误，请重试";
            model.addAttribute("errorString", errorString);
            model.addAttribute("eventtitle",receive.getDescription());
            return "event/getpoint";
        }

        Account account = accountService.getAccount(qq);
        if(account != null) {
            //先判断重复领取
            receive.setQq(qq);
            Pointlog pointlog = new Pointlog();
            pointlog.setOperated_qq_id(qq);
            pointlog.setOperate_point(receive.getPoint());
            pointlog.setOperate_type(1);
            pointlog.setType(3);
            pointlog.setDescription(receive.getDescription() + "——扫码");
            return oprateRecieve(receive, pointlog, model);

//            if(eventService.getReceive(receive) != null) {
//                System.out.println(qq + "：重复领取贝化值");
//                String errorString = "每个账号仅限领取一次，请勿重复领取";
//                model.addAttribute("errorString", errorString);
//                return "event/getpoint";
//            }
//            else {
//                Pointlog pointlog = new Pointlog();
//                pointlog.setOperated_qq_id(qq);
//                pointlog.setOperate_point(100);
//                pointlog.setOperate_type(3);
//                pointlog.setDescription("中秋节线下聚会福利扫码领取");
//                try {
//                    eventService.insertReceive(receive);
//                    pointService.insertPointlog(pointlog);
//                    return "success";
//                } catch (Exception e) {
//                    String errorString = "兑换失败！请重试或联系管理员解决";
//                    model.addAttribute("errorString", errorString);
//                    return "fail";
//                }
//            }

        }
        else {
            errorString = "该QQ号码未注册，请注册后重新扫码领取";
            model.addAttribute("errorString", errorString);
            return "account/newfan";
        }
    }

    private String oprateRecieve(Receive receive, Pointlog pointlog, Model model) {
        String errorString;
        if(eventService.getReceive(receive) != null) {
            System.out.println(receive.getQq() + "：重复领取贝化值");
            errorString = "每个账号仅限领取一次，请勿重复领取";
            model.addAttribute("errorString", errorString);
            return "fail";
        }
        else {
            try {
                eventService.insertReceive(receive);
                pointService.insertPointlog(pointlog);
                errorString = "兑换成功！请登录查收";
//                errorString = "未到兑换时间，还不能兑换哦";
//                model.addAttribute("eventtitle",receive.getDescription());
//                return "event/getpoint";
                model.addAttribute("errorString", errorString);
                return "account/login";
            } catch (Exception e) {
                errorString = "兑换失败！请重试或联系管理员解决";
                model.addAttribute("errorString", errorString);
                model.addAttribute("eventtitle",receive.getDescription());
                return "event/getpoint";
            }
        }
    }

    @GetMapping("event/activity/nianzhongchoujiang")
    public String viewActivity_nianzhongchoujiang(HttpSession session, Model model) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            Account account = accountService.getAccount(qq);
            Receive receive_nianzhongchoujiang1 = new Receive("QM1231","伊莎贝拉应援会年终福利签名照抽奖资格",0);
            Receive receive_nianzhongchoujiang2 = new Receive("ZB1231","伊莎贝拉应援会年终福利周边抽奖资格",0);
            receive_nianzhongchoujiang1.setQq(qq);
            receive_nianzhongchoujiang2.setQq(qq);
            List<Receive> receiveList1 = eventService.getReceiveListByEvent_code("QM1231");
            List<Receive> receiveList2 = eventService.getReceiveListByEvent_code("ZB1231");
            List<Pointlog> pointlogList = pointService.getPointlogListByQq(qq);
            int maxpoint = 0;
            int flag1 = 0;
            int flag2 = 0;
            if(eventService.getReceive(receive_nianzhongchoujiang1) != null) {
                flag1 = 1;
            }
            if(eventService.getReceive(receive_nianzhongchoujiang2) != null) {
                flag2 = 1;
            }
            for(Pointlog pointlog : pointlogList) {
                if(pointlog.getOperate_type() == 1) {
                    maxpoint += pointlog.getOperate_point();
                }
            }
            if(maxpoint < 5000) {
                flag1 = 2;
            }
            if(maxpoint < 100) {
                flag2 = 2;
            }
            if(account.getState() == 0) {
                flag1 = 3;
                flag2 = 3;
            }
            flag1 = 1;
            flag2 = 1;
            model.addAttribute("mymaxpoint",maxpoint);
            session.setAttribute("mymaxpoint",maxpoint);
            model.addAttribute("flag1",flag1);
            model.addAttribute("flag2",flag2);
            model.addAttribute("joinnum1",receiveList1.size());
            model.addAttribute("joinnum2",receiveList2.size());
            return "event/activity/nianzhongchoujiang";
        } else
            return "redirect:/";
    }

    @PostMapping("event/activity/nianzhongchoujiang1")
    public ModelAndView postActivity_nianzhongchoujiang1(HttpSession session, Model model) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            Account account = accountService.getAccount(qq);
            String nianzhongchoujiang_string1;
            int maxpoint = (int) session.getAttribute("mymaxpoint");
            session.removeAttribute("mymaxpoint");
            if(maxpoint >= 5000) {
                Receive receive_nianzhongchoujiang1 = new Receive("QM1231","伊莎贝拉应援会年终福利签名照抽奖资格",0);
                receive_nianzhongchoujiang1.setQq(qq);
                try {
                    eventService.insertReceive(receive_nianzhongchoujiang1);
                } catch (Exception e) {
                    String errorString = "报名失败！请重试或联系管理员解决";
                    model.addAttribute("errorString", errorString);
                    return new ModelAndView("fail");
                }
                nianzhongchoujiang_string1 = "报名抽奖成功！活动时开奖，敬请期待";
                model.addAttribute("nianzhongchoujiang_string",nianzhongchoujiang_string1);
                return new ModelAndView("redirect:/event/activity/nianzhongchoujiang");
            }
            else {
                nianzhongchoujiang_string1 = "抱歉，您没有抽奖资格哦！";
                model.addAttribute("nianzhongchoujiang_string",nianzhongchoujiang_string1);
                return new ModelAndView("redirect:/event/activity/nianzhongchoujiang");
            }
        } else
            return new ModelAndView("redirect:/");
    }

    @PostMapping("event/activity/nianzhongchoujiang2")
    public ModelAndView postActivity_nianzhongchoujiang2(HttpSession session, Model model) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            String nianzhongchoujiang_string2;
            int maxpoint = (int) session.getAttribute("mymaxpoint");
            session.removeAttribute("mymaxpoint");
            if(maxpoint >= 100) {
                Receive receive_nianzhongchoujiang2 = new Receive("ZB1231","伊莎贝拉应援会年终福利周边抽奖资格",0);
                receive_nianzhongchoujiang2.setQq(qq);
                try {
                    eventService.insertReceive(receive_nianzhongchoujiang2);
                } catch (Exception e) {
                    String errorString = "报名失败！请重试或联系管理员解决";
                    model.addAttribute("errorString", errorString);
                    return new ModelAndView("fail");
                }
                nianzhongchoujiang_string2 = "报名抽奖成功！活动时开奖，敬请期待";
                model.addAttribute("nianzhongchoujiang_string",nianzhongchoujiang_string2);
                return new ModelAndView("redirect:/event/activity/nianzhongchoujiang");
            }
            else {
                nianzhongchoujiang_string2 = "抱歉，您没有抽奖资格哦！";
                model.addAttribute("nianzhongchoujiang_string",nianzhongchoujiang_string2);
                return new ModelAndView("redirect:/event/activity/nianzhongchoujiang");
            }
        } else
            return new ModelAndView("redirect:/");
    }

}
