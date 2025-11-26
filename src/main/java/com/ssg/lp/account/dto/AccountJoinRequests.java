package com.ssg.lp.account.dto;

import com.ssg.lp.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountJoinRequests {
    private String loginId;
    private String loginPw;
    private String name;
    private String address;

    public Member toMember() {
        return new Member(loginId, loginPw, name, address);
    }
}
