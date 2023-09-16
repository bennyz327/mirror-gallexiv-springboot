package com.team.gallexiv.security;

import cn.hutool.core.util.StrUtil;
import com.team.gallexiv.common.exception.JwtTokenException;
import com.team.gallexiv.common.utils.JwtUtils;
import com.team.gallexiv.common.utils.RedisUtil;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

import static com.team.gallexiv.common.lang.Const.LOGIN_URI;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    GallexivUserDetailsService securityUserDetailService;

    @Autowired
    UserService userService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, JwtException {

        log.info("開始辨識使用者");
        String jwt = request.getHeader(jwtUtils.getHeader());
        if (StrUtil.isBlankOrUndefined(jwt)) {
            log.info("無token 即將跳過驗證階段");
            chain.doFilter(request, response);
            return;
        }

        try {
            Claims claim = jwtUtils.getClaimByToken(jwt);
            if (claim == null) {
                log.info("token 異常，請重新登入");
                throw new JwtException("token 異常");
            }
            if (jwtUtils.isTokenExpired(claim)) {
                log.info("token 已過期，請重新登入");
                throw new JwtException("token 已過期");
            }
            if (!securityUserDetailService.checkJwtClaimValidInRedis(claim)) {
                log.info("token 無效，請重新登入");
                throw new JwtException("token 無效");
            }

            String username = claim.getSubject();
            log.info("驗證使用者成功 歡迎 {}", username);
            log.info("本使用者前端token過期時間: {}", claim.getExpiration());

            //從受信任的使用者名稱去資料庫獲取使用者權限資料
            Userinfo sysUser = userService.getUserByAccount(username);
            //將使用者資料封裝成 Authentication 的實現類 其中包含將使用者權限資料緩存到redis
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, securityUserDetailService.getUserAuthority(sysUser.getUserId()));

            //將 Authentication 設定到 SecurityContext
            log.info("取代預設放入 SecurityContext 中的 Authentication");
            SecurityContextHolder.getContext().setAuthentication(token);

            //log印出資料已驗證使用者清單，確認是否成功
            log.info("以下使用者已存入SecurityContext: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            log.info("本帳號角色+權限: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());

            whenTokenValid(request,response,chain);

        } catch (JwtException e) {
            log.info("token異常--->{}", e.getMessage());
            //若token異常，則需要跳轉到驗證失敗處理器
            loginFailureHandler.onAuthenticationFailure(request, response, new JwtTokenException(e.getMessage()));
        }

    }


    private void whenTokenValid(HttpServletRequest request, HttpServletResponse response,FilterChain chain) throws IOException, ServletException {
        // 都會刷新token
        if (request.getRequestURI().equals(LOGIN_URI)) {
            log.info("重複登入，即將跳轉到登入成功處理器");
            loginSuccessHandler.onAuthenticationSuccess(request, response, SecurityContextHolder.getContext().getAuthentication());
        } else {
            //若非驗證端點則呼叫doFilter繼續檢查，理論上會進入權限檢查
            log.info("驗證成功，即將放行");
            chain.doFilter(request, response);
        }
    }
}
