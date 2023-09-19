package com.team.gallexiv.security;

import cn.hutool.json.JSONUtil;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.common.utils.JwtUtils;
import com.team.gallexiv.data.model.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.team.gallexiv.common.lang.Const.JWT_HEADER;

@Slf4j
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    UserService userS;

    @Autowired
    GallexivUserDetailsService gallexivUserDetailsService;

    //TODO 權限拒絕有生效，要測試權限允許是否正常
    @Autowired
    JwtUtils jwtUtils;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("進入登出成功處理器");

        // 1.盡量在回應中消除標頭的TOKEN
        // 2.前端也需主動將自己定位成未登入，棄用原本的舊的TOKEN
        // 3.權限驗證時檢查REDIS資料是否過期，若沒有則視為舊TOKEN，並提示重新登入

        Authentication nowAuthentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("嘗試從驗證儲存中清除使用者權限資訊的緩存");
        if (authentication != null) {
            log.info("即將登出的使用者為:{}", authentication.getName());
            userS.clearUserAuthorityInfo(authentication.getName());
        }else if (nowAuthentication != null) {
            log.info("即將登出的使用者為:{}", nowAuthentication.getName());
            userS.clearUserAuthorityInfo(nowAuthentication.getName());
        }else {
            log.info("無法從驗證資料取得登出的使用者");
        }

        log.info("嘗試從jwt中取得使用者資訊以清除緩存");
        String jwt = request.getHeader(jwtUtils.getHeader());
        if (jwt != null) {
            try {
                Claims claims = jwtUtils.getClaimByToken(jwt);
                if(gallexivUserDetailsService.checkJwtClaimValidInRedis(claims)){
                    log.info("jwt有效，可進行登出");
                    String account = claims.getSubject();
                    log.info("即將清除的使用者為:{}", account);
                    userS.clearUserAuthorityInfo(account);
                    log.info("即將清除驗證儲存的登入資訊");
                    new SecurityContextLogoutHandler().logout(request, response, nowAuthentication);

                }

            } catch (Exception e) {
                log.info("透過jwt清除緩存失敗");
            }
        }else {
            log.info("透過jwt清除緩存失敗");
        }


        response.setContentType("application/json;charset=UTF-8");
        log.info("即將清除標頭的token");
        response.setHeader(jwtUtils.getHeader(), "");
        ServletOutputStream out = response.getOutputStream();
        //對於前端來說，保密操作結果即可
        out.write(JSONUtil.toJsonStr(VueData.ok("操作完成")).getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
