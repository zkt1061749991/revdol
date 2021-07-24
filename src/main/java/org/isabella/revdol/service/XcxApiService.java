package org.isabella.revdol.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.isabella.revdol.domin.*;
import org.isabella.revdol.util.xcxapi.GetIdolList;

import java.util.ArrayList;
import java.util.List;

public class XcxApiService {
    public static XcxData getIdolData() {
        JSONObject jsonObject = GetIdolList.getIdol();
        XcxData xcxData = new XcxData();
        xcxData.setFans_num(jsonObject.getInt("fans_num"));
        xcxData.setAttention_num(jsonObject.getInt("attention_num"));
        xcxData.setPopular_num(jsonObject.getInt("popular_num"));
        return xcxData;
    }

    public static List<XcxData> getEventImgList() {
        JSONObject jsonObject = GetIdolList.getEventList();
        JSONObject object = jsonObject.getJSONObject("data");
        JSONArray jsonArray = object.getJSONArray("items");
        int num = jsonArray.size();
        List<XcxData> list = new ArrayList<>();
        for(int i =0;i<num;i++) {
            XcxData xcxData = new XcxData();
            xcxData.setImg_url(jsonArray.getJSONObject(i).getString("banner_url"));
            list.add(xcxData);
        }
        return list;
    }

    public static XcxData getWeekData() {
        JSONArray jsonArray = GetIdolList.getWeek();
        int no = 1;
        XcxData xcxData = new XcxData();
        for (int i = 0 ;i<6;i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(jsonObject.getInt("idol_id") == 4) {
                xcxData.setWeek_no(no);
                xcxData.setWeek_popular_num(jsonObject.getInt("popular_num"));
                xcxData.setWeek_string(jsonObject.getString("time"));
                JSONObject object = jsonObject.getJSONObject("idol");
                xcxData.setPopular_num(object.getInt("popular_num"));
                xcxData.setFans_num(object.getInt("fans_num"));
                xcxData.setAttention_num(object.getInt("attention_num"));
                return xcxData;
            }
            else {
                no++;
            }
        }
        return xcxData;
    }

    public static BData getBFansNum(String mid) {
        JSONObject jsonObject = GetIdolList.getBFansNum(mid);
        JSONObject object = jsonObject.getJSONObject("data");
        JSONObject object1 = object.getJSONObject("card");
        BData bData = new BData();
        bData.setFans(object1.getInt("fans"));
        return bData;
    }

    public static boolean getForum(int user_id, Forum forum, Member member, Contribute contribute) {
        boolean flag = false;
        for (int i = 1;i <= 7; i++) {
            try {
                JSONObject jsonObject = GetIdolList.getIdolforums(i);
                JSONArray jsonArray_items = jsonObject.getJSONArray("items");
                int num = jsonArray_items.size();
                for (int j = 0; j < num; j++) {
                    if (jsonArray_items.getJSONObject(j).getInt("user_id") == user_id) {
                        JSONObject item = jsonArray_items.getJSONObject(j);
                        forum.setId(item.getInt("id"));
                        forum.setUser_id(item.getInt("user_id"));
                        forum.setType(item.getInt("type"));
                        forum.setTitle(item.getString("title"));
                        forum.setContent(item.getString("content"));
                        forum.setVid(item.getString("vids"));
                        forum.setPraise_counts(item.getInt("praise_counts"));
                        forum.setIs_pick(item.getString("is_pick"));
                        forum.setTag(item.getInt("tag"));
                        forum.setStatus(item.getInt("status"));
                        forum.setCreated_time(item.getString("created_time"));
                        forum.setThumbs(item.getJSONObject("forum_picture").getJSONArray("thumbs"));
                        forum.setImages(item.getJSONObject("forum_picture").getJSONArray("images"));
                        System.out.println(forum.toString());

                        JSONObject jsonObject_member = item.getJSONObject("member");
                        member.setId(jsonObject_member.getInt("id"));
                        member.setOpenid(jsonObject_member.getString("openid"));
                        member.setUid(jsonObject_member.getInt("uid"));
                        member.setSex(jsonObject_member.getInt("sex"));
                        member.setNickname(jsonObject_member.getString("nickname"));
                        member.setBirth(jsonObject_member.getString("birth"));
                        member.setHeadimg(jsonObject_member.getString("headimg"));
                        member.setAddress(jsonObject_member.getString("address"));
                        member.setSignature(jsonObject_member.getString("signature"));
                        member.setStatus(jsonObject_member.getInt("status"));
                        System.out.println(member.toString());

                        JSONObject jsonObject_contribute = item.getJSONObject("contribute");
                        contribute.setId(jsonObject_contribute.getInt("id"));
                        contribute.setUser_id(jsonObject_contribute.getInt("user_id"));
                        contribute.setPoint(jsonObject_contribute.getInt("point"));
                        contribute.setUser_point(jsonObject_contribute.getInt("user_point"));
                        contribute.setLevel(jsonObject_contribute.getInt("level"));
                        System.out.println(contribute.toString());
                        return true;
                    }
                }
            } catch (Exception e) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static boolean isForumDel(int id) {
        JSONObject jsonObject = GetIdolList.getforum(id);
        if(jsonObject.containsKey("error")) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Forum forum = new Forum();
        Member member = new Member();
        Contribute contribute = new Contribute();
        getForum(17556,forum,member,contribute);
    }
}
