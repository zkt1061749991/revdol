package org.isabella.revdol.service.impl;

import org.isabella.revdol.domin.Receive;
import org.isabella.revdol.persistence.EventMapper;
import org.isabella.revdol.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    @Autowired
    private EventMapper eventMapper;

    @Override
    public List<Receive> getReceiveListByEvent_code(String event_code) {
        return eventMapper.getReceiveListByEvent_code(event_code);
    }

    @Override
    public Receive getReceive(Receive receive) {
        return eventMapper.getReceive(receive);
    }

    @Override
    public void insertReceive(Receive receive) {
        eventMapper.insertReceive(receive);
    }
}
