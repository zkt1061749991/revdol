package org.isabella.revdol.persistence;

import org.isabella.revdol.domin.Pointlog;

import java.util.List;

public interface PointMapper {
    List<Pointlog> getPointlogListByQq(String qq);
    void insertPointlog(Pointlog pointlog);
}
