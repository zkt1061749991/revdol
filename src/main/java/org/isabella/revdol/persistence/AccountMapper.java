package org.isabella.revdol.persistence;

import org.isabella.revdol.domin.Account;
import org.isabella.revdol.domin.Contribute;
import org.isabella.revdol.domin.Member;

import java.util.List;

public interface AccountMapper {
    List<Account> getAccountList();

    Account getAccount(String qq);

    Account getAccountByXcxuid(String uid);

    void updateState(Account account);

    void updateXcxuid(Account account);

    void insertAccount(Account account);

    void updateAccount(Account account);

    void delateAccount(String qq);

    Member getMember(int id);

    Member getMemberByUid(int uid);

    Contribute getContribute(int id);

    boolean memberExist(int id);

    void insertMember(Member member);

    void updateMember(Member member);

    boolean contributeExist(int id);

    void insertContribute(Contribute contribute);

    void updateContribute(Contribute contribute);

}
