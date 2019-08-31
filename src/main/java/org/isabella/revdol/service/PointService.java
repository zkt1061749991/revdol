package org.isabella.revdol.service;

import org.isabella.revdol.domin.Pointlog;

import java.util.List;

public interface PointService {
    List<Pointlog> getPointlogListByQq(String qq);
    void insertPointlog(Pointlog pointlog);
}
