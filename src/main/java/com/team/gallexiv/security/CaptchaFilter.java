package com.team.gallexiv.security;

import com.team.gallexiv.common.exception.CaptchaException;
import com.team.gallexiv.common.utils.RedisUtil;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.team.gallexiv.common.lang.Const.CAPTCHA_KEY;
import static com.team.gallexiv.common.lang.Const.LOGIN_URI;

@Slf4j
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if (LOGIN_URI.equals(url) && request.getMethod().equals("POST")) {
            log.info("獲取 Login Chain 正在效驗驗證碼 -- " + url);
            try {
                validate(request);
                filterChain.doFilter(request, response);
            } catch (CaptchaException e) {
                log.info(e.getMessage());
                // 交给登錄失敗處理器
                loginFailureHandler.onAuthenticationFailure(request, response, e);
            }
        } else {
            filterChain.doFilter(request, response);
        }
//        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {
        String code = request.getParameter("code");
        String token = request.getParameter("token");
        if (StringUtil.isNullOrEmpty(code) || StringUtil.isNullOrEmpty(token)) {
            throw new CaptchaException("驗證碼不能為空");
        }
        log.info("code = " + code);
        log.info("token = " + token);
        if (!code.equals(redisUtil.hget(CAPTCHA_KEY, token))) {
            throw new CaptchaException("驗證碼錯誤");
        }
        System.out.println("驗證碼正確");
        // 一次性使用，驗證碼從Redis刪除
        redisUtil.hdel(CAPTCHA_KEY, token);
    }
}