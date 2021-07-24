package org.isabella.revdol.persistence;

import org.isabella.revdol.domin.Cdkey;
import org.isabella.revdol.domin.Image;
import org.isabella.revdol.domin.Topic;
import org.isabella.revdol.domin.Zf;

import java.util.List;

public interface ResourceMapper {
    List<Image> getImageListByForum_id(int id);
    List<Image> getThumbListByForum_id(int id);
    Image getImageByImg(String img);
    void insertImage(Image image);
    Cdkey getCdkey(String cdkey);
    void useCdkey(String cdkey);

    List<Topic> getZfList();
    Topic getZfTopic(Topic topic);
    Boolean ZfTopicExist(Topic topic);
    void insertZfTopic(Topic topic);
    List<Topic> getSendTopicList();
    List<Topic> getActTopicList();
    Zf getLastZf(Zf zf);
    void insertZf(Zf zf);
}
