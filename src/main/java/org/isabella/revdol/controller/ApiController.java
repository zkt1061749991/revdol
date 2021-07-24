package org.isabella.revdol.controller;

import net.sf.json.JSONArray;
import org.isabella.revdol.domin.*;
import org.isabella.revdol.service.XcxApiService;
import org.isabella.revdol.service.impl.AccountServiceImpl;
import org.isabella.revdol.service.impl.PointServiceImpl;
import org.isabella.revdol.service.impl.ResourceServiceImpl;
import org.isabella.revdol.util.CommonReturnType;
import org.isabella.revdol.util.Getpic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@RestController
public class ApiController {
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PointServiceImpl pointService;
    @Autowired
    private ResourceServiceImpl resourceService;

    public ApiController() {

    }

    @GetMapping("api/getaccount")
    public CommonReturnType getPointByaccount(@RequestParam(name = "qq", required = true) String qq) {
        Account account = accountService.getAccount(qq);
        if (account != null) {
            account.setAddress("");
            account.setHiddenadd("");
            account.setRemark("");
            account.setWeixin("");
            return CommonReturnType.create(account);
        } else {
            Map<String, String> failmap = new HashMap<>();
            failmap.put("errMsg", "未找到对应贝化值账号");
            failmap.put("errNum", "100");
            return CommonReturnType.create(failmap, "fail");
        }
    }

    @PostMapping("api/autoforumbook")
    public CommonReturnType autoforumbookByaccount(@RequestParam(name = "qq", required = true) String qq) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("autoforumbook api post:" + qq + "  " + df.format(d));
        Account account = accountService.getAccount(qq);
        Map<String, String> infomap = new HashMap<>();

        if (account != null) {
            if (!account.getXcxuid().equals("")) {
                List<AllForum> forumList = pointService.getForumListByXcxuid(account.getXcxuid());
                List<AllForum> bookedforumList = new ArrayList<AllForum>();
                if (!forumList.isEmpty()) {
                    int suc = 0;
                    for (AllForum forum : forumList) {
                        if (forum.getTag() != 1 && forum.getTag() != 7) {

                            //判断是否删了
                            boolean flag = false;
                            try {
                                flag = XcxApiService.isForumDel(forum.getId());
                            } catch (Exception e) {
                                infomap.put("errMsg", "服务器内部错误：连接失败");
                                infomap.put("errNum", "201");
                                return CommonReturnType.create(infomap, "fail");
                            }
                            //删了则处理下一个帖子
                            if (flag) {
                                continue;
                            }

                            if (!pointService.bookedForumExist(forum.getId())) {
                                Pointlog pointlog = new Pointlog();
                                pointlog.setState(0);
                                pointlog.setOperated_qq_id(qq);
                                pointlog.setOperate_type(1);
                                pointlog.setOperate_point(0);
                                pointlog.setExplain("来自蛋仔客服的一键登记");
                                pointlog.setType(0);
                                pointlog.setForum_id(forum.getId());
                                pointlog.setRemark("http://isabella.revdol.club/point/forum?key=mark&id=" + forum.getId());
                                if (!forum.getVid().isEmpty()) {
                                    pointlog.setRemark("http://isabella.revdol.club/point/forum?key=mark&id=" + forum.getId() + "        视频链接： https://v.qq.com/x/page/" + forum.getVid() + ".html");
                                }
                                List<Image> imageList = resourceService.getImageListByForum_id(forum.getId());

                                if (imageList.size() != 0) {
                                    pointlog.setImg(imageList.get(0).getImg());

                                    //储存图片至服务器本地
                                    String filePath = "/revdol/media/";
                                    Getpic pic = new Getpic();
                                    for (Image image : imageList) {
                                        String url = image.getUrl();
                                        String fileName = image.getImg();
                                        pic.saveUrlAs(url, filePath + fileName);
                                    }
                                }

                                String title = forum.getTitle();
                                if (title.length() > 18) title = title.substring(0, 18) + "……";
                                switch (forum.getTag()) {
//                                    case 1:
//                                        pointlog.setDescription("活动发帖：" + title);
//                                        pointlog.setType(1);
//                                        break;
                                    case 2:
                                        pointlog.setDescription("二创：表情包 " + title);
                                        break;
                                    case 3:
                                        pointlog.setDescription("二创：同人文 " + title);
                                        break;
                                    case 4:
                                        pointlog.setDescription("二创：同人图 " + title);
                                        break;
                                    case 5:
                                        pointlog.setDescription("二创：Cosplay " + title);
                                        break;
                                    case 6:
                                        pointlog.setDescription("二创：手工制品 " + title);
                                        break;
//                                    case 7:
//                                        pointlog.setDescription(title);
//                                        break;
                                    case 8:
                                        pointlog.setDescription("二创：翻唱翻跳 " + title);
                                        break;
                                    case 9:
                                        pointlog.setDescription("二创：视频类 " + title);
                                        break;
                                    default:
                                        pointlog.setDescription("未识别的类型帖：" + title);
                                }
                                try {
                                    pointService.insertbookedForum(forum);
                                    pointService.insertPointlog(pointlog);
                                    forum.setImages(JSONArray.fromObject(imageList));
                                    bookedforumList.add(forum);
                                    suc += 1;
                                } catch (Exception e) {
                                    infomap.put("errMsg", "服务器内部错误：数据库错误");
                                    infomap.put("errNum", "202");
                                    return CommonReturnType.create(infomap, "fail");
                                }
                            }
                            //普通帖则判断是否为活动帖
                        } else {
//                                List<Topic> topicList = resourceService.getActTopicList();
//                                if(!topicList.isEmpty()) {
//                                    forum.getTitle().contains()
//                                }

                        }
                    }
                    if (suc == 0) {
                        infomap.put("errMsg", "未获取到新的可登记二创");
                        infomap.put("errNum", "400");
                        return CommonReturnType.create(infomap, "success");
                    } else {
                        return CommonReturnType.create(bookedforumList,"success");
                    }
                } else {
                    infomap.put("errMsg", "未获取到帖子列表");
                    infomap.put("errNum", "401");
                    return CommonReturnType.create(infomap, "success");
                }
            } else {
                infomap.put("errMsg", "未绑定小程序战姬众号(UID)");
                infomap.put("errNum", "101");
                return CommonReturnType.create(infomap, "success");
            }
        } else {
            infomap.put("errMsg", "未找到对应贝化值账号");
            infomap.put("errNum", "100");
            return CommonReturnType.create(infomap, "fail");
        }
    }

    @PostMapping("api/setxcxuid")
    public CommonReturnType setXcxuidByAccount(@RequestParam(name = "qq", required = true) String qq,@RequestParam(name = "uid", required = true) String uid) {
        Map<String, String> infomap = new HashMap<>();

        if(!isInteger(uid)) {
            infomap.put("errMsg", "参数不合法：UID格式错误");
            infomap.put("errNum", "402");
            return CommonReturnType.create(infomap, "fail");
        }

        Account account = accountService.getAccount(qq);
        if (account != null) {
            if (account.getXcxuid().equals("")) {
                if (!accountService.XcxuidExist(uid)) {
                    account.setXcxuid(uid);
                    Member member = accountService.getMemberByUid(Integer.valueOf(uid));
                    if(member != null) {
                        account.setXcxname(member.getNickname());
                    } else {
                        account.setXcxname("");
                    }

                    accountService.updateXcxuid(account);

                    infomap.put("Msg", "绑定成功");
                    infomap.put("uid", account.getXcxuid());
                    return CommonReturnType.create(infomap, "success");
                } else {
                    infomap.put("Msg", "该小程序UID已被其他账号绑定，请核对或联系管理");
                    return CommonReturnType.create(infomap, "fail");
                }
            } else {
                infomap.put("Msg", "该账号已绑定小程序");
                infomap.put("uid", account.getXcxuid());
                return CommonReturnType.create(infomap, "fail");
            }
        } else {
            infomap.put("errMsg", "未找到对应贝化值账号");
            infomap.put("errNum", "100");
            return CommonReturnType.create(infomap, "fail");
        }
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
