package com.team.gallexiv.security;

import cn.hutool.json.JSONUtil;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.common.utils.JwtUtils;
import com.team.gallexiv.data.model.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登出成功");
//        userS.clearUserAuthorityInfo(authentication.getName());
        // 清除登入資訊
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader(jwtUtils.getHeader(), "");
        ServletOutputStream out = response.getOutputStream();
        VueData result = VueData.ok();
        out.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
