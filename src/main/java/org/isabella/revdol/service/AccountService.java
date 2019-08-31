package org.isabella.revdol.service;


import org.isabella.revdol.domin.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAccountList();
    Account getAccount(String qq);
    void updateState(Account account);
    void insertAccount(Account Account);
    void updateAccount(Account Account);
    boolean accountExist(String qq);
}
