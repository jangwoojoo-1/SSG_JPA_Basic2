package com.ssg.lp.member.service;

import com.ssg.lp.member.entity.Member;
import com.ssg.lp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member find(String loginId, String loginPw) {
        Optional<Member> member = memberRepository.findByLoginIdAndLoginPw(loginId, loginPw);

        return member.orElse(null);
    }

    @Override
    public void delete(Integer id) {
        memberRepository.deleteById(id);
    }

    @Override
    public Member findById(Integer id) {
        return memberRepository.findById(id).orElse(null);
    }
}
