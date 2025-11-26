package com.ssg.lp.member.service;

import com.ssg.lp.member.entity.Member;

public interface MemberService {
    void save(Member member);

    Member find(String loginId, String loginPw);

    void delete(Integer id);

    Member findById(Integer id);

    void change(Member member);
}
