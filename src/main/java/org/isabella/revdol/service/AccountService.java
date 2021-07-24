package org.isabella.revdol.service;


import org.isabella.revdol.domin.Account;
import org.isabella.revdol.domin.Contribute;
import org.isabella.revdol.domin.Member;

import java.util.List;

public interface AccountService {
    List<Account> getAccountList();
    Account getAccount(String qq);
    Account getAccountByXcxuid(String uid);
    boolean XcxuidExist(String uid);
    void updateState(Account account);
    void updateXcxuid(Account account);
    void insertAccount(Account Account);
    void updateAccount(Account Account);
    boolean accountExist(String qq);
    Member getMember(int id);
    Member getMemberByUid(int uid);
    boolean memberExist(int id);
    void insertMember(Member member);
    void updateMember(Member member);
    Contribute getContribute(int id);
    boolean contributeExist(int id);
    void insertContribute(Contribute contribute);
    void updateContribute(Contribute contribute);
}
