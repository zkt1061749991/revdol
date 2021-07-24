package org.isabella.revdol.service.impl;

import org.isabella.revdol.domin.Cdkey;
import org.isabella.revdol.domin.Image;
import org.isabella.revdol.domin.Topic;
import org.isabella.revdol.domin.Zf;
import org.isabella.revdol.persistence.ResourceMapper;
import org.isabella.revdol.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Image> getImageListByForum_id(int id) {
        return resourceMapper.getImageListByForum_id(id);
    }

    @Override
    public List<Image> getThumbListByForum_id(int id) {
        return resourceMapper.getThumbListByForum_id(id);
    }

    @Override
    public boolean imageExist(String img) {
        if(resourceMapper.getImageByImg(img) != null)
            return true;
        else return false;
    }

    @Override
    public void insertImage(Image image) {
        resourceMapper.insertImage(image);
    }

    @Override
    public Cdkey getCdkey(String cdkey) {
        return resourceMapper.getCdkey(cdkey);
    }

    @Override
    public void useCdkey(String cdkey) {
        resourceMapper.useCdkey(cdkey);
    }



    @Override
    public List<Topic> getZfList() {
        return resourceMapper.getZfList();
    }

    @Override
    public Topic getZfTopic(Topic topic) {
        return resourceMapper.getZfTopic(topic);
    }

    @Override
    public Boolean ZfTopicExist(Topic topic) {
        if(resourceMapper.getZfTopic(topic) != null)
            return true;
        else return false;
    }

    @Override
    public void insertZfTopic(Topic topic) {
        resourceMapper.insertZfTopic(topic);
    }

    @Override
    public List<Topic> getSendTopicList() {
        return  resourceMapper.getSendTopicList();
    }

    @Override
    public List<Topic> getActTopicList() {
        return resourceMapper.getActTopicList();
    }

    @Override
    public Zf getLastZf(Zf zf) {
        return resourceMapper.getLastZf(zf);
    }


    @Override
    public void insertZf(Zf zf) {
        resourceMapper.insertZf(zf);
    }
}
