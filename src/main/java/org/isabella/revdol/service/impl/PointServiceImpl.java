package org.isabella.revdol.service.impl;

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
}
