package com.ssg.lp.account.controller;

import com.ssg.lp.account.dto.AccountJoinRequests;
import com.ssg.lp.account.dto.AccountLoginRequests;
import com.ssg.lp.account.dto.AccountUpdateRequests;
import com.ssg.lp.account.helper.AccountHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lp")
public class AccountController {
    private final AccountHelper accountHelper;

    // 회원 가입
    @PostMapping("/api/account/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequests joinReq) {
        //입력값이 비어있다면
        if(joinReq.getName() == null || joinReq.getLoginId() == null || joinReq.getLoginPw() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accountHelper.join(joinReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //로그인
    @PostMapping("/api/account/login")
    public  ResponseEntity<?> login(HttpServletRequest request, @RequestBody AccountLoginRequests loginReq) {
        //입력값이 비어있다면
        if(loginReq.getLoginId() == null || loginReq.getLoginPw() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String output = accountHelper.login(loginReq, request);
        if(output == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/api/account/id")
    public ResponseEntity<?> getId(HttpServletRequest request) {
        return new ResponseEntity<>(accountHelper.getAccountId(request), HttpStatus.OK);
    }

    // 로그인 여부 확인
    @GetMapping("/api/account/check")
    public ResponseEntity<?> check(HttpServletRequest request) {
        return new ResponseEntity<>(accountHelper.isLoggedIn(request), HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/api/account/logout")
    public  ResponseEntity<?> logout(HttpServletRequest request) {
        accountHelper.logout(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //회원탈퇴
    @DeleteMapping("/api/account/withdraw")
    public ResponseEntity<?> withdraw(HttpServletRequest request) {
        Integer id = accountHelper.getAccountId(request);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        accountHelper.withdraw(id);
        accountHelper.logout(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/account/info")
    public ResponseEntity<?> getInfo(HttpServletRequest request) {
        return new ResponseEntity<>(accountHelper.getInfo(request), HttpStatus.OK);
    }

    //회원 정보 수정
    @PutMapping("/api/account/info")
    public ResponseEntity<?> change(HttpServletRequest request, @RequestBody AccountUpdateRequests updateRequest) {
        Integer memberId = accountHelper.getAccountId(request);
        if (memberId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        accountHelper.update(memberId, updateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
