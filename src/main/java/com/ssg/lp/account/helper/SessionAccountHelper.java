package com.ssg.lp.account.helper;

import com.ssg.lp.account.dto.AccountJoinRequests;
import com.ssg.lp.account.dto.AccountLoginRequests;
import com.ssg.lp.account.dto.AccountUpdateRequests;
import com.ssg.lp.account.etc.AccountConstants;
import com.ssg.lp.common.util.HttpUtils;
import com.ssg.lp.member.entity.Member;
import com.ssg.lp.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class SessionAccountHelper implements AccountHelper {
    private final MemberService memberService;

    @Override
    @Transactional
    public void join(AccountJoinRequests joinRequests) {
        Member member = joinRequests.toMember();
        memberService.save(member);
    }

    @Override
    public String login(AccountLoginRequests loginRequests, HttpServletRequest request, HttpServletResponse response) {
        Member member = memberService.find(loginRequests.getLoginId(), loginRequests.getLoginPw());

        if (member == null) {
            return null;
        } else {
            HttpUtils.setSession(request, AccountConstants.ACCOUNT_ID, member.getId());
            return member.getLoginId();
        }
    }

    @Override
    public Integer getAccountId(HttpServletRequest request) {
        Object accountId = HttpUtils.getSession(request, AccountConstants.ACCOUNT_ID);
        if (accountId == null) {
            return null;
        } else {
            return Integer.parseInt(accountId.toString());
        }
    }

    @Override
    public boolean isLoggedIn(HttpServletRequest request) {
        return HttpUtils.getSession(request, AccountConstants.ACCOUNT_ID) != null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpUtils.removeSession(request, AccountConstants.ACCOUNT_ID);
    }

    @Override
    @Transactional
    public void withdraw(Integer id) {
        memberService.delete(id);
    }

    @Override
    public Member getInfo(HttpServletRequest request) {
        Object accountId = HttpUtils.getSession(request, AccountConstants.ACCOUNT_ID);
        if (accountId == null) {
            return null;
        } else {
            return memberService.findById(Integer.parseInt(accountId.toString()));
        }
    }

    @Override
    public void update(Integer id, AccountUpdateRequests accountRequests) {
        Member member = accountRequests.toEntity(id);
        memberService.change(member);
    }
}
