package com.ssg.lp.member.repository;

import com.ssg.lp.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByLoginIdAndLoginPw(String loginId, String loginPw);
}
