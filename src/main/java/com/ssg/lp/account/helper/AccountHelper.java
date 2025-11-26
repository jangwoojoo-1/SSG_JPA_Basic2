package com.ssg.lp.account.helper;

import com.ssg.lp.account.dto.AccountJoinRequests;
import com.ssg.lp.account.dto.AccountLoginRequests;
import com.ssg.lp.account.dto.AccountUpdateRequests;
import com.ssg.lp.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;

public interface AccountHelper {
    void join(AccountJoinRequests joinRequests);

    String login(AccountLoginRequests loginRequests, HttpServletRequest request);

    Integer getAccountId(HttpServletRequest request);

    boolean isLoggedIn(HttpServletRequest request);

    void logout(HttpServletRequest request);

    void withdraw(Integer id);

    Member getInfo(HttpServletRequest request);

    void update(Integer Id, AccountUpdateRequests accountRequests);
}
