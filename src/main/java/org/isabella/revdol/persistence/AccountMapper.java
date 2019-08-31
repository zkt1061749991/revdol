package org.isabella.revdol.persistence;

import org.isabella.revdol.domin.Account;

import java.util.List;

public interface AccountMapper {
    List<Account> getAccountList();

    Account getAccount(String qq);

    void updateState(Account account);

    void insertAccount(Account account);

    void updateAccount(Account account);

    void delateAccount(String qq);

}
