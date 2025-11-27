package com.ssg.lp.common.interceptor;

import com.ssg.lp.account.helper.AccountHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component // ① 스프링 컨테이너에서 관리하는 컴포넌트
@RequiredArgsConstructor
public class ApiInterceptor implements HandlerInterceptor {
    private final AccountHelper accountHelper;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        // 로그인 회원 아이디가 없으면
        if (accountHelper.getAccountId(req) == null) {
            res.setStatus(401);
            return false;
        }

        return true;
    }
}