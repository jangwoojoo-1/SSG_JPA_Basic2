package com.ssg.lp.account.dto;

import com.ssg.lp.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountJoinRequests {
    private String name;
    private String loginId;
    private String loginPw;
    private String address;

    public Member toMember() {
        return new Member(name, loginId, loginPw, address);
    }
}
