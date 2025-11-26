package com.ssg.lp.account.dto;

import com.ssg.lp.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateRequests {
    private String loginId;
    private String loginPw;
    private String name;
    private String address;

    public Member toEntity(Integer id) {
        return new Member(id, loginId, loginPw, name, address);
    }
}
