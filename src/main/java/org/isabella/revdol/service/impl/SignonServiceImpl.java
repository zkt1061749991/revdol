package org.isabella.revdol.service.impl;

import org.isabella.revdol.domin.Signon;
import org.isabella.revdol.persistence.SignonMapper;
import org.isabella.revdol.service.AccountService;
import org.isabella.revdol.service.SignonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SignonServiceImpl implements SignonService{
    @Autowired
    private SignonMapper signonMapper;

    @Override
    public List<Signon> getSignonList() {
        return signonMapper.getSignonList();
    }

    @Override
    public Signon getSignon(String qq,String password) {
        Signon signon = new Signon();
        signon.setQq(qq);
        signon.setPassword(password);
        return signonMapper.getSignon(signon);
    }

    @Override
    public void insertSignon(Signon signon) {
        signonMapper.insertSignon(signon);
    }

    @Override
    public void updateSignon(Signon signon) {
        signonMapper.updateSignon(signon);
    }

}
