package org.isabella.revdol.service.impl;

import org.isabella.revdol.domin.Account;
import org.isabella.revdol.domin.Contribute;
import org.isabella.revdol.domin.Member;
import org.isabella.revdol.persistence.AccountMapper;
import org.isabella.revdol.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> getAccountList() {
        return accountMapper.getAccountList();
    }

    @Override
    public Account getAccount(String qq) {
        return accountMapper.getAccount(qq);
    }

    @Override
    public Account getAccountByXcxuid(String uid) {
        return accountMapper.getAccountByXcxuid(uid);
    }

    @Override
    public boolean XcxuidExist(String uid) {
        if(accountMapper.getAccountByXcxuid(String.valueOf(uid)) != null)
            return true;
        else return false;
    }

    @Override
    public void updateState(Account account) {
        accountMapper.updateState(account);
    }

    @Override
    public void updateXcxuid(Account account) {
        accountMapper.updateXcxuid(account);
    }


    @Override
    public void insertAccount(Account Account) {
        accountMapper.insertAccount(Account);
    }

    @Override
    public void updateAccount(Account Account) {
        accountMapper.updateAccount(Account);
    }

    @Override
    public boolean accountExist(String qq){
        if(accountMapper.getAccount(qq) != null)
            return true;
        else return false;
    }

    @Override
    public Member getMember(int id) {
        return null;
    }

    @Override
    public Member getMemberByUid(int uid) {
        return accountMapper.getMemberByUid(uid);
    }

    @Override
    public boolean memberExist(int id) {
        if(accountMapper.getMember(id) != null)
            return true;
        else return false;
    }

    @Override
    public void insertMember(Member member) {
        accountMapper.insertMember(member);
    }

    @Override
    public void updateMember(Member member) {
        accountMapper.updateMember(member);
    }

    @Override
    public Contribute getContribute(int id) {
        return null;
    }

    @Override
    public boolean contributeExist(int id) {
        if(accountMapper.getContribute(id) != null)
            return true;
        else return false;
    }

    @Override
    public void insertContribute(Contribute contribute) {
        accountMapper.insertContribute(contribute);
    }

    @Override
    public void updateContribute(Contribute contribute) {
        accountMapper.updateContribute(contribute);
    }


}
