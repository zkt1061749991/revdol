package org.isabella.revdol.service;

import org.isabella.revdol.domin.Receive;

import java.util.List;

public interface EventService {
    List<Receive> getReceiveListByEvent_code(String event_code);
    Receive getReceive(Receive receive);
    void insertReceive(Receive receive);
}
