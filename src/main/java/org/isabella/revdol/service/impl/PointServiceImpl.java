package org.isabella.revdol.service.impl;

import org.isabella.revdol.domin.AllForum;
import org.isabella.revdol.domin.Forum;
import org.isabella.revdol.domin.Pointlog;
import org.isabella.revdol.persistence.PointMapper;
import org.isabella.revdol.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PointServiceImpl implements PointService {
    @Autowired
    private PointMapper pointMapper;

    @Override
    public List<Pointlog> getPointlogListByQq(String qq) {
        return pointMapper.getPointlogListByQq(qq);
    }

    @Override
    public void insertPointlog(Pointlog pointlog) {
        pointMapper.insertPointlog(pointlog);
    }

    @Override
    public Forum getForum(int id) {
        return pointMapper.getForum(id);
    }

    @Override
    public boolean forumExist(int id) {
        if(pointMapper.getForum(id) != null) {
            return true;
        }
        else return false;
    }

    @Override
    public void insertForum(Forum forum) {
        pointMapper.insertForum(forum);
    }

    @Override
    public AllForum getAllForum(int id) {
        return pointMapper.getAllForum(id);
    }

    @Override
    public List<AllForum> getForumListByXcxuid(String xcxuid) {
        return pointMapper.getForumListByXcxuid(xcxuid);
    }

    @Override
    public Forum getbookedForum(int id) {
        return pointMapper.getbookedForum(id);
    }

    @Override
    public boolean bookedForumExist(int id) {
        if(pointMapper.getbookedForum(id) != null) {
            return true;
        }
        else return false;
    }

    @Override
    public void insertbookedForum(AllForum forum) {
        pointMapper.insertbookedForum(forum);
    }
}
