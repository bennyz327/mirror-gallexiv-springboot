package com.team.gallexiv.security;

import cn.hutool.core.util.StrUtil;
import com.team.gallexiv.common.utils.JwtUtils;
import com.team.gallexiv.data.model.UserService;
import com.team.gallexiv.data.model.Userinfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.team.gallexiv.common.lang.Const.LOGIN_URI;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    GallexivUserDetailsService securityUserDetailService;

    @Autowired
    UserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("開始辨識使用者");
        String jwt = request.getHeader(jwtUtils.getHeader());
        if (StrUtil.isBlankOrUndefined(jwt)) {
            log.info("無token 即將跳過驗證階段");
            chain.doFilter(request, response);
            return;
        }

        Claims claim = jwtUtils.getClaimByToken(jwt);
        if (claim == null) {
            log.info("token 異常，請重新登入");
            throw new JwtException("token 異常");
        }
        if (jwtUtils.isTokenExpired(claim)) {
            log.info("token 已過期，請重新登入");
            throw new JwtException("token 已過期");
        }

        String username = claim.getSubject();
        log.info("驗證使用者成功 歡迎 {}", username);
        log.info("本使用者前端token過期時間: {}", claim.getExpiration());

        //從受信任的使用者名稱去資料庫獲取使用者權限資料
        Userinfo sysUser = userService.getUserByAccount(username);
        //將使用者資料封裝成 Authentication 的實現類
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(username, null, securityUserDetailService.getUserAuthority(sysUser.getUserId()));

        //將 Authentication 設定到 SecurityContext
        SecurityContextHolder.getContext().setAuthentication(token);

        //log印出資料已驗證使用者清單，確認是否成功
        log.info("以下使用者已存入SecurityContext: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());


        // 若TOKEN驗證成功，且端點為驗證端點，不要再呼叫doFilter去經過後面的檢查
        // 直接跳轉到登入成功處理器刷新token
        if (request.getRequestURI().equals(LOGIN_URI)) {
            log.info("重複登入，即將跳轉到登入成功處理器");
            loginSuccessHandler.onAuthenticationSuccess(request, response, SecurityContextHolder.getContext().getAuthentication());
        } else {
            //若非驗證端點則呼叫doFilter繼續檢查，理論上會進入權限檢查
            log.info("登入成功，即將放行");
            chain.doFilter(request, response);
        }
        // TODO 若重複登入的話新舊給的TOKEN變成都能用，要想辦法讓舊的TOKEN失效
        // TODO 基本款
        // 前端主動刪除舊的TOKEN並替換成新的TOKEN
        // TODO 額外安全升級
        // 權限驗證時檢查REDIS是否有該使用者權限資料，若沒有則視為舊TOKEN，並提示重新登入
    }
}
