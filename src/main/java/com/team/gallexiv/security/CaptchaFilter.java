package com.team.gallexiv.security;

import com.team.gallexiv.utils.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Slf4j
//@Component
//public class CaptchaFilter extends OncePerRequestFilter {
//
//    private final static String LOGIN_URL = "/login";

//    @Autowired
//    RedisUtil redisUtil;
//    @Autowired
//    LoginFailureHandler loginFailureHandler;

//    @Override
//    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
//
//    }
//}
