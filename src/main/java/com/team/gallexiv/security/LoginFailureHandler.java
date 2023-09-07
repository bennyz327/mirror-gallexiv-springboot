package com.team.gallexiv.security;

import cn.hutool.json.JSONUtil;
import com.team.gallexiv.common.exception.CaptchaException;
import com.team.gallexiv.common.exception.JwtTokenException;
import com.team.gallexiv.common.lang.Const;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.common.utils.RedisUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.team.gallexiv.common.lang.Const.CAPTCHA_REDIS_KEY;

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException, IOException {

        //只要驗證碼或者帳密錯誤，就會進入這個方法
        log.info("進入登錄失敗處理器");

        //有異常就輸出在日誌中
        if (!Objects.isNull(exception)) {
            log.info("異常類型: {}", exception.getClass());
            log.info("異常訊息: {}", exception.getMessage());
        }
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();


        if (Objects.equals(exception.getClass(), CaptchaException.class)) {
            if(!Objects.equals(exception.getMessage(), "驗證碼不能為空")) {
                //如果是嘗試後錯誤，須重新請求驗證碼
                log.info("清除驗證碼紀錄");
                redisUtil.hdel(CAPTCHA_REDIS_KEY, request.getParameter("token"));
            }
            VueData result = VueData.error("非法驗證碼，請刷新驗證碼");
            outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
            return;
        }


        if (Objects.equals(exception.getClass(), BadCredentialsException.class)) {
            VueData result = VueData.error("查無用户名或密碼错误");
            log.info("查無用户名或密碼错误");
            log.info("本次驗證的使用者名稱: {}", request.getParameter(Const.USERNAME_PARAM));
            log.info("本次驗證的密碼: {}", request.getParameter(Const.PASSWORD_PARAM));
            outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
            return;
        }


        if (Objects.equals(exception.getClass(), JwtTokenException.class)) {
            //如果錯誤訊息為token無效，則清除回應中的token
            if(Objects.equals(exception.getMessage(),"token 無效")){
                log.info("清除回應中的JWT標頭");
                response.setHeader(Const.JWT_HEADER, "");
            }
            VueData result = VueData.error(exception.getMessage());
            outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
            return;
        }

        log.error("無異常或未處理的異常進入登錄失敗處理器");
        outputStream.flush();
        outputStream.close();
    }
}
