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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    GallexivUserDetailsService securityUserDetailService;

    @Autowired
    UserService userService;


    //TODO 不確定這樣注入Manager是否能行
    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("開始驗證使用者");
        String jwt = request.getHeader(jwtUtils.getHeader());
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }

        Claims claim = jwtUtils.getClaimByToken(jwt);
        if (claim == null) {
            throw new JwtException("token 異常");
        }
        if (jwtUtils.isTokenExpired(claim)) {
            throw new JwtException("token 已過期");
        }

        String username = claim.getSubject();
        log.info("已驗證使用者為: {}", username);

        //從資料庫獲取使用者權限資料
        Userinfo sysUser = userService.getUserByAccount(username);
        //將使用者資料封裝成 UserDetails
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(username, null, securityUserDetailService.getUserAuthority(sysUser.getUserId()));

        //將 UserDetails 設定到 SecurityContext
        SecurityContextHolder.getContext().setAuthentication(token);

        //log印出資料已驗證使用者清單，確認是否成功
        log.info("以下使用者已存入SecurityContext: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        chain.doFilter(request, response);
    }
}
