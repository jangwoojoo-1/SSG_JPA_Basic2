package com.ssg.lp.account.controller;

import com.ssg.lp.account.dto.AccountJoinRequests;
import com.ssg.lp.account.dto.AccountLoginRequests;
import com.ssg.lp.account.dto.AccountUpdateRequests;
import com.ssg.lp.account.etc.AccountConstants;
import com.ssg.lp.account.helper.AccountHelper;
import com.ssg.lp.block.service.BlockService;
import com.ssg.lp.common.util.HttpUtils;
import com.ssg.lp.common.util.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lp")
public class AccountController {
    private final AccountHelper accountHelper;
    private final BlockService blockService;

    // 회원 가입
    @PostMapping("/api/account/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequests joinReq) {
        // 입력 값이 비어 있다면
        if (!StringUtils.hasLength(joinReq.getName()) || !StringUtils.hasLength(joinReq.getLoginId()) || !StringUtils.hasLength(joinReq.getLoginPw())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accountHelper.join(joinReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //로그인
    @PostMapping("/api/account/login")
    public  ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountLoginRequests loginReq) {
        // 입력 값이 비어 있다면
        if (!StringUtils.hasLength(loginReq.getLoginId()) || !StringUtils.hasLength(loginReq.getLoginPw())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String output = accountHelper.login(loginReq, request, response);
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
    public  ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        accountHelper.logout(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // ② 액세스 토큰을 재발급하는 메서드. @GetMapping 애너테이션을 지정하여 HTTP GET요청을 매핑하고, 연결 경로로 /api/account/token 을 지정한다.
//   쿠키의 리프레시 토큰을 조회하고 이 값이 유효하다면 이 토큰을 활용하여 액세스 토큰을 발급하고 리턴한다.

    @GetMapping("/api/account/token")
    public ResponseEntity<?> regenerate(HttpServletRequest req) {
        String accessToken = "";
        String refreshToken = HttpUtils.getCookieValue(req, AccountConstants.REFRESH_TOKEN_NAME);

        // 리프레시 토큰이 유효하다면
        if (StringUtils.hasLength(refreshToken) && TokenUtils.isValid(refreshToken) && !blockService.has(refreshToken)) {
            // 리프레시 토큰의 내부 값 조회
            Map<String, Object> tokenBody = TokenUtils.getBody(refreshToken);

            // 리프레시 토큰의 회원 아이디 조회
            Integer memberId = (Integer) tokenBody.get(AccountConstants.ACCOUNT_ID);

            // 액세스 토큰 발급
            accessToken = TokenUtils.generate(AccountConstants.ACCESS_TOKEN_NAME, AccountConstants.ACCOUNT_ID, memberId, AccountConstants.ACCESS_TOKEN_EXP_MINUTES);
        }

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    //회원탈퇴
    @DeleteMapping("/api/account/withdraw")
    public ResponseEntity<?> withdraw(HttpServletRequest request, HttpServletResponse response) {
        Integer id = accountHelper.getAccountId(request);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        accountHelper.withdraw(id);
        accountHelper.logout(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/account/info")
    public ResponseEntity<?> getInfo(HttpServletRequest request) {
        return new ResponseEntity<>(accountHelper.getInfo(request), HttpStatus.OK);
    }

    //회원 정보 수정
    @PutMapping("/api/account/info")
    public ResponseEntity<?> change(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountUpdateRequests updateRequest) {
        Integer memberId = accountHelper.getAccountId(request);
        if (memberId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        accountHelper.update(memberId, updateRequest);
        accountHelper.logout(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
