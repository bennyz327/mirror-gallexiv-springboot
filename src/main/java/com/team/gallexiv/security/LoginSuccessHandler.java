package com.team.gallexiv.security;

import cn.hutool.json.JSONUtil;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.common.utils.JwtUtils;
import com.team.gallexiv.common.utils.RedisUtil;
import com.team.gallexiv.data.model.UserService;
import com.team.gallexiv.data.model.Userinfo;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userS;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        log.info("登入成功處理器");

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        // 生成jwt
        // 密鑰透過環境變數取得
        // 將jwt的過期時間順便記錄到redis
        String jwt = jwtUtils.generateToken(authentication.getName());
        response.setHeader(jwtUtils.getHeader(), jwt);

        //到這之前要確定使用者有註冊進入後台資料庫，不然下面查詢會報錯
        Integer userId = userS.getUserByAccount(authentication.getName()).getUserId();

        log.info("完成登入流程");
        //透過ID將驗證資訊查出來並寫入redis，順便印出來
        log.info("帳號權限為： {}",userS.getUserAuthorityInfo(userId));

        VueData result = VueData.ok("登入成功");
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}

