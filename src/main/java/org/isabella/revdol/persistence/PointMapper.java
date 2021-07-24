package org.isabella.revdol.persistence;

import org.isabella.revdol.domin.AllForum;
import org.isabella.revdol.domin.Forum;
import org.isabella.revdol.domin.Pointlog;

import java.util.List;

public interface PointMapper {
    List<Pointlog> getPointlogListByQq(String qq);
    void insertPointlog(Pointlog pointlog);
    Forum getForum(int id);
    boolean forumExist(int id);
    void insertForum(Forum forum);
    AllForum getAllForum(int id);

    List<AllForum> getForumListByXcxuid(String xcxuid);
    Forum getbookedForum(int id);
    void insertbookedForum(AllForum forum);
}
