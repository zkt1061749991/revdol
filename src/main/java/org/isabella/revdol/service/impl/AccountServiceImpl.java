package org.isabella.revdol.service.impl;

import org.isabella.revdol.domin.Account;
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
    public void updateState(Account account) {
        accountMapper.updateState(account);
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

}
