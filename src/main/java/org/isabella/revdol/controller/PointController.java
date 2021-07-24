package org.isabella.revdol.controller;


import org.isabella.revdol.domin.*;
import org.isabella.revdol.service.XcxApiService;
import org.isabella.revdol.service.impl.*;
import org.isabella.revdol.util.Getpic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Controller
public class PointController {
    @Autowired
    private SignonServiceImpl signonService;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private PointServiceImpl pointService;
    @Autowired
    private ResourceServiceImpl resourceService;
    @Autowired
    private EventServiceImpl eventService;

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

    @GetMapping("point/forum")
    public String commentForum(Model model, HttpSession session,@RequestParam("id") int id,@RequestParam("key") String key) {
        if(key.equals("mark")) {
            //AllForum forum = pointService.getAllForum(id);
            Forum forum = pointService.getForum(id);
            if (forum != null) {
                String tag;
                List<Image> thumbsList = resourceService.getThumbListByForum_id(forum.getId());
                List<Image> imagesList = resourceService.getImageListByForum_id(forum.getId());
                switch (forum.getTag()) {
                    case 1:
                        tag = "普通文字帖";
                        break;
                    case 2:
                        tag = "表情包 基础分50/张 最高3张";
                        break;
                    case 3:
                        tag = "同人文 基础分100 字数，主角是不是贝拉，剧情";
                        break;
                    case 4:
                        tag = "同人图 基础分100，多层上色200起 / 同人漫画基础分200";
                        break;
                    case 5:
                        tag = "Cosplay 基础分500";
                        break;
                    case 6:
                        tag = "手工制品 基础分300，旧作品改进重投往低评";
                        break;
                    case 7:
                        tag = "普通视频帖";
                        break;
                    case 8:
                        tag = "翻唱翻跳视频 基础分500";
                        break;
                    case 9:
                        tag = "二创视频 先判断类型：原创基础分350，剪辑基础分100";
                        break;
                    default:
                        tag = "未识别的帖子类型";
                }
                model.addAttribute("thumbsList", thumbsList);
                model.addAttribute("imagesList", imagesList);
                model.addAttribute("tag", tag);
                model.addAttribute("forum", forum);
                return "point/forum";
            } else return "redirect:/";
        }
        else return "redirect:/";
    }


    @GetMapping("point/show_forum")
    public String viewForum(Model model, HttpSession session,@RequestParam("id") int id) {
        String qq = (String) session.getAttribute("qq");
        if(qq != null) {
            Forum forum = pointService.getForum(id);
            if (forum != null) {
                String tag;
                List<Image> thumbsList = resourceService.getThumbListByForum_id(forum.getId());
                List<Image> imagesList = resourceService.getImageListByForum_id(forum.getId());
                switch (forum.getTag()) {
                    case 1:
                        tag = "普通文字帖";
                        break;
                    case 2:
                        tag = "表情包";
                        break;
                    case 3:
                        tag = "同人文";
                        break;
                    case 4:
                        tag = "同人图";
                        break;
                    case 5:
                        tag = "Cosplay";
                        break;
                    case 6:
                        tag = "手工制品";
                        break;
                    case 7:
                        tag = "普通视频帖";
                        break;
                    case 8:
                        tag = "翻唱翻跳视频";
                        break;
                    case 9:
                        tag = "二创视频";
                        break;
                    default:
                        tag = "未识别的帖子类型";
                }
                model.addAttribute("thumbsList", thumbsList);
                model.addAttribute("imagesList", imagesList);
                model.addAttribute("tag", tag);
                model.addAttribute("forum", forum);
                return "point/forum";
            } else return "redirect:/error";
        }
        else return "redirect:/";
    }

    @GetMapping("point/forum_book")
    public String viewbook(Model model, HttpSession session) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            Account account = accountService.getAccount(qq);
            model.addAttribute("account", account);
            return "point/forum_book";
        } else
            return "redirect:/";
    }

    @GetMapping("point/getforum")
    public String getForum(Model model, HttpSession session) throws IOException {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            Account account = accountService.getAccount(qq);
            model.addAttribute("account", account);
            Forum forum = new Forum();
            Member member = new Member();
            Contribute contribute = new Contribute();
            String tag;
            String displaystring;
            if(account.getXcxuid() == null || account.getXcxuid().equals("")) {
                displaystring = "请先填写小程序战姬众号UID";
                model.addAttribute("account", account);
                model.addAttribute("displaystring",displaystring);
                return "point/forum_book";
            }
            int uid = Integer.valueOf(account.getXcxuid());
            if (XcxApiService.getForum(uid, forum, member, contribute)) {

                account.setXcxname(member.getNickname());
                if (account.getState() == 0 && contribute.getUser_point() > 1386) {
                    account.setState(1);
                    accountService.updateState(account);
                }
                accountService.updateAccount(account);
                if (!accountService.memberExist(member.getId())) {
                    accountService.insertMember(member);
                } else {
                    accountService.updateMember(member);
                }
                if (!accountService.contributeExist(contribute.getId())) {
                    accountService.insertContribute(contribute);
                } else {
                    accountService.updateContribute(contribute);
                }
                int state = 0; //0可登记，1不可登记

                switch (forum.getTag()) {
                    case 1:
                        if(forum.getTitle().contains("#歌姬的会议#")) {
                            tag = "活动发帖";
                            displaystring = "点击按钮登记至贝化值系统";
                            break;
                        } else {
                            tag = "普通文字帖";
                            displaystring = "尚未开放该类型帖的登记";
                            state = 1;
                            break;
                        }
                    case 2:
                        tag = "表情包";
                        displaystring = "点击按钮登记至贝化值系统";
                        break;
                    case 3:
                        tag = "同人文";
                        displaystring = "点击按钮登记至贝化值系统";
                        break;
                    case 4:
                        tag = "同人图";
                        displaystring = "点击按钮登记至贝化值系统";
                        break;
                    case 5:
                        tag = "Cosplay";
                        displaystring = "点击按钮登记至贝化值系统";
                        break;
                    case 6:
                        tag = "手工制品";
                        displaystring = "点击按钮登记至贝化值系统";
                        break;
                    case 7:
                        tag = "普通视频帖";
                        displaystring = "尚未开放该类型帖的登记";
                        state = 1;
                        break;
                    case 8:
                        tag = "翻唱翻跳视频";
                        displaystring = "点击按钮登记至贝化值系统";
                        break;
                    case 9:
                        tag = "二创视频";
                        displaystring = "点击按钮登记至贝化值系统";
                        break;
                    default:
                        tag = "未识别的帖子类型";displaystring = "尚未开放该类型帖的登记";state = 1;
                }

                List<Image> thumbsList = resourceService.getThumbListByForum_id(forum.getId());
                List<Image> imagesList = resourceService.getImageListByForum_id(forum.getId());
                System.out.println(thumbsList);
//                List<Object> thumbs = new ArrayList<>();
//                List<Object> images = new ArrayList<>();

                if (thumbsList.size() != 0 && imagesList.size() != 0) {
                    //获取图片名称
//                    for (Image thumb : thumbsList) {
//                        thumbs.add(thumb.getImg());
//                    }
//                    for (Image image : imagesList) {
//                        images.add(image.getImg());
//                    }
                } else {
                    if (!forum.getThumbs().isEmpty() && !forum.getImages().isEmpty()) {
                        Getpic pic = new Getpic();
                        String filePath = "/revdol/media/";
                        //将图片缓存至服务器并建立文件列表
                        /*
                        for (Object thumb : forum.getThumbs()) {
                            String url = (String) thumb;
                            String fileName = url.substring(url.lastIndexOf("/"));
                            pic.saveUrlAs(url, filePath + fileName);
                            Image image = new Image();
                            image.setForum_id(forum.getId());
                            image.setImg(fileName.substring(1));
                            image.setType(0);
                            image.setUid(forum.getUser_id());
                            image.setUrl(url);

                            URL urlx = new URL(url);
                            URLConnection connection = urlx.openConnection();
                            connection.setDoOutput(true);
                            BufferedImage bufferedImage = ImageIO.read(connection.getInputStream());
                            image.setWidth(String.valueOf(bufferedImage.getWidth()));      // 源图宽度
                            image.setHeight(String.valueOf(bufferedImage.getHeight()));      // 源图高度

                            resourceService.insertImage(image);
//                            thumbs.add(fileName.substring(1));
                            thumbsList.add(image);
                        }
                        */

                        for (Object image : forum.getImages()) {
                            String url = (String) image;
                            String fileName = url.substring(url.lastIndexOf("/"));
                            pic.saveUrlAs(url, filePath + fileName);


                                Image image1 = new Image();
                                image1.setForum_id(forum.getId());
                                image1.setImg(fileName.substring(1));
                                image1.setType(1);
                                image1.setUid(forum.getUser_id());
                                image1.setUrl(url);

                                URL urlx = new URL(url);
                                URLConnection connection = urlx.openConnection();
                                connection.setDoOutput(true);
                                BufferedImage bufferedImage = ImageIO.read(connection.getInputStream());
                                image1.setWidth(String.valueOf(bufferedImage.getWidth()));      // 源图宽度
                                image1.setHeight(String.valueOf(bufferedImage.getHeight()));      // 源图高度
                            if(!resourceService.imageExist(image1.getImg())) {
                                resourceService.insertImage(image1);
                            }
//                            images.add(fileName.substring(1));
                                imagesList.add(image1);

                        }
                    }
                }
                if(pointService.forumExist(forum.getId())) {
                    state = 1;
                    displaystring = "这个帖子已经登记了哦";
                }
//                model.addAttribute("images", images);
//                model.addAttribute("thumbs",thumbs);
//                System.out.println(thumbs.toString());
                model.addAttribute("thumbsList",thumbsList);
                model.addAttribute("imagesList",imagesList);
                session.setAttribute("forum", forum);
                model.addAttribute("forum", forum);
                model.addAttribute("tag", tag);
                model.addAttribute("state",state);
                model.addAttribute("displaystring",displaystring);

                return "point/forum_look";
            } else {
                return "point/forum_fail";
            }
        } else
            return "redirect:/";
    }


    @PostMapping("point/getforum")
    public String postForum(Model model, HttpSession session, @RequestParam("explain") String explain) {
        String qq = (String) session.getAttribute("qq");
        Forum forum = (Forum) session.getAttribute("forum");
        session.removeAttribute("forum");
        if (qq != null) {
            if(pointService.forumExist(forum.getId())) {
                String errorString = "这个帖子已经登记过啦";
                model.addAttribute("errorString", errorString);
                return "point/fail";
            }
            else {
                Pointlog pointlog = new Pointlog();
                pointlog.setState(0);
                pointlog.setOperated_qq_id(qq);
                pointlog.setOperate_type(1);
                pointlog.setOperate_point(0);
                pointlog.setExplain(explain);
                pointlog.setType(0);
                pointlog.setForum_id(forum.getId());
                pointlog.setRemark("http://isabella.revdol.club/point/forum?key=mark&id=" + forum.getId());
                if(!forum.getVid().equals("")) {
                    pointlog.setRemark("http://isabella.revdol.club/point/forum?key=mark&id=" + forum.getId() + "    视频链接： https://v.qq.com/x/page/" + forum.getVid() + ".html");
                }
                List<Image> imageList = resourceService.getImageListByForum_id(forum.getId());
                if (imageList.size() != 0) {
                    pointlog.setImg(imageList.get(0).getImg());
                }

                String title = forum.getTitle();
                if (title.length() > 18) title = title.substring(0, 18) + "……";
                switch (forum.getTag()) {
                    case 1:
                        pointlog.setDescription("活动发帖：" + title);
                        pointlog.setType(1);
                        break;
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
                    case 7:
                        pointlog.setDescription(title);
                        break;
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
                    pointService.insertForum(forum);
                    pointService.insertPointlog(pointlog);
                    model.addAttribute("title", title);
                    return "point/success";
                } catch (Exception e) {
                    return "redirect:/error";
                }

            }
        }
        return "redirect:/error";
    }

    @GetMapping("point/cdkey")
    public String viewcdkey(Model model, HttpSession session) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            model.addAttribute("qq",qq);
            return "point/cdkey";
        }
        else {
            return "redirect:/";
        }
    }

    @PostMapping("point/cdkey")
    public String usecdkey(Model model, HttpSession session, @RequestParam("cdkey") String cdkey) {
        String qq = (String) session.getAttribute("qq");
        if (qq != null) {
            String stateString;
            model.addAttribute("qq",qq);
            Cdkey cdkey1 = resourceService.getCdkey(cdkey);
            if(cdkey1 != null) {
                if(cdkey1.getState() == 0) {
                    Receive receive = new Receive(cdkey1.getCdkey(),"兑换码：" + cdkey1.getDescription(),cdkey1.getPoint());
                    receive.setQq(qq);
//                    try {
                        switch (cdkey1.getType()) {
                            case 0:
                            case 1:
                            case 3:
                                eventService.insertReceive(receive);
                                Pointlog pointlog = new Pointlog();
                                pointlog.setOperated_qq_id(qq);
                                pointlog.setOperate_type(1);
                                pointlog.setType(3);
                                pointlog.setOperate_point(cdkey1.getPoint());
                                pointlog.setDescription("通过兑换码获取 " + cdkey1.getDescription());
                                pointService.insertPointlog(pointlog);
                                resourceService.useCdkey(cdkey1.getCdkey());
                                break;
                            case 2:
                                eventService.insertReceive(receive);
                                resourceService.useCdkey(cdkey1.getCdkey());
                                break;
                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    stateString = "兑换成功！";
                    model.addAttribute("stateString",stateString);
                }
                else {
                    stateString = "兑换码过期或已使用";
                    model.addAttribute("stateString",stateString);
                }
            }
            else {
                stateString = "兑换码不存在或输入错误";
                model.addAttribute("stateString",stateString);
            }
            return "point/cdkey";
        }
        else {
            return "redirect:/";
        }
    }
}
