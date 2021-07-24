package org.isabella.revdol.persistence;

import org.isabella.revdol.domin.Receive;

import java.util.List;

public interface EventMapper {
    List<Receive> getReceiveListByEvent_code(String event_code);
    Receive getReceive(Receive receive);
    void insertReceive(Receive receive);
}
