package com.team.gallexiv.security;

import cn.hutool.json.JSONUtil;
import com.team.gallexiv.common.lang.VueData;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        log.info("權限不足");

        ServletOutputStream outputStream = response.getOutputStream();

        VueData result = VueData.error(accessDeniedException.getMessage());

        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();

    }
}
