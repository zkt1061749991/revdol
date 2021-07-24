package org.isabella.revdol.controller;

import org.isabella.revdol.domin.Account;
import org.isabella.revdol.domin.Order;
import org.isabella.revdol.domin.Pointlog;
import org.isabella.revdol.domin.Prize;
import org.isabella.revdol.service.impl.AccountServiceImpl;
import org.isabella.revdol.service.impl.PointServiceImpl;
import org.isabella.revdol.service.impl.PrizeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PrizeController {
    @Autowired
    private PrizeServiceImpl prizeService;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PointServiceImpl pointService;

    @GetMapping("shop/prize")
    public String viewPrize(Model model, HttpSession session) {
        session.removeAttribute("prize_id");
        List<Prize> prizeList = prizeService.getPrizeList();
        model.addAttribute("prizeList", prizeList);
        return "shop/prize";
    }

    @GetMapping("shop/detail")
    public String viewDetail(@RequestParam("id") String id, Model model, HttpSession session) {
        session.removeAttribute("prize_id");
        Prize prize = prizeService.getPrize(id);
        model.addAttribute("prize", prize);
        return "shop/detail";
    }

    @GetMapping("shop/confirm")
    public String confirm(@RequestParam("id") String prize_id,HttpSession session, Model model) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            Account account = accountService.getAccount(qq);
            account.hiddenadd();
            Prize prize = prizeService.getPrize(prize_id);
            session.setAttribute("prize_id", prize.getId());
            model.addAttribute("prize", prize);
            model.addAttribute("account", account);
            return "shop/confirm";
        } else
            return "redirect:/";
    }

    @PostMapping("shop/buy")
    public String buy(@RequestParam("currunt_prize_id") String currunt_prize_id,HttpSession session, Model model) {
        String qq = (String) session.getAttribute("qq");
        Integer id = (Integer) session.getAttribute("prize_id");
        Prize prize = prizeService.getPrize(currunt_prize_id);

        if(qq != null) {
            Account account = accountService.getAccount(qq);
            if (account.getState() != 0) {
                if (id != null) {
                    if (prize != null) {
                        if (prize.getId() == id) {
                            String cannot = "";
                            Order order = new Order();
                            order.setQq_id(qq);
                            order.setPrize_id(prize.getId());
                            order.setPoint(prize.getPoint());
//                            order.setAddress(account.getAddress());
                            order.setAddress("已停止记录，发货时QQ联系");
                            account.hiddenadd();
//                            if (order.getAddress() == null || order.getAddress().equals("")) {
//                                cannot = "兑换地址呢？请先填写兑换地址，gkd";
//                                model.addAttribute("prize", prize);
//                                model.addAttribute("account", account);
//                                model.addAttribute("cannot", cannot);
//                                return "shop/confirm";
//                            }
                            order.setNumber(1);
                            order.setPrize_name(prize.getPrize_name());
                            if (prize.getQuantity() >= 1) {
                                if (account.getPoint() >= order.getPoint()) {
                                    int quntity = prize.getQuantity();
                                    quntity = quntity - 1;
                                    prize.setQuantity(quntity);
                                    Pointlog pointlog = new Pointlog();
                                    pointlog.setOperated_qq_id(qq);
                                    pointlog.setOperate_point(order.getPoint());
                                    pointlog.setType(4);
                                    pointlog.setOperate_type(3);
                                    pointlog.setDescription("贝化值兑换奖品：" + order.getPrize_name());
                                    account.setState(3);
                                    try {
                                        pointService.insertPointlog(pointlog);
                                        accountService.updateState(account);
                                        prizeService.updatePrize(prize);
                                        prizeService.insertOrder(order);
                                        session.removeAttribute("prize_id");
                                        model.addAttribute("prize", prize);
                                        return "shop/success";
                                    } catch (Exception e) {
                                        String buyerrorString = "兑换失败！请重试或联系管理员解决";
                                        model.addAttribute("buyerrorString", buyerrorString);
                                        return "shop/fail";
                                    }
                                } else {
                                    cannot = "这位蛋仔，贝化值不够兑换诶，快快参与活动和二创积累贝化值吧！";
                                    model.addAttribute("prize", prize);
                                    model.addAttribute("account", account);
                                    model.addAttribute("cannot", cannot);
                                    return "shop/confirm";
                                }
                            } else {
                                cannot = "啊啊啊！该奖品已经被兑换完了，不要灰心，以后还会有更多奖品哦";
                                model.addAttribute("prize", prize);
                                model.addAttribute("account", account);
                                model.addAttribute("cannot", cannot);
                                return "shop/confirm";
                            }
                        } else {
                            String buyerrorString = "哦哟，出错啦！你这是什么骚操作？给我好好整啊";
                            model.addAttribute("buyerrorString", buyerrorString);
                            return "shop/fail";
                        }
                    } else {
                        String buyerrorString = "喂喂喂，你要的奖品我没找到啊";
                        model.addAttribute("buyerrorString", buyerrorString);
                        return "shop/fail";
                    }
                } else {
                    String buyerrorString = "哦哟，出错啦！请勿重复提交同一兑换请求";
                    model.addAttribute("buyerrorString", buyerrorString);
                    return "shop/fail";
                }
            }
            else {
                String buyerrorString = "账号处于待审核状态，你还没有完成小程序贡献值的审核，不可以兑换奖品哦";
                model.addAttribute("buyerrorString", buyerrorString);
                return "shop/fail";
            }
        }
        else {
            return "redirect:/error";
        }

    }
}
