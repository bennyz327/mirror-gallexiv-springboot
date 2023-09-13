package com.team.gallexiv.security;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.json.JSONUtil;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.common.utils.JwtUtils;
import com.team.gallexiv.common.utils.RedisUtil;
import com.team.gallexiv.data.dto.PreRegisterUserinfo;
import com.team.gallexiv.data.model.UserService;
import com.team.gallexiv.data.model.Userinfo;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.team.gallexiv.common.lang.Const.FRONTEND_URL_MAP;

@Slf4j
@Component
public class OauthLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userS;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        log.info("登入成功");

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        // 生成jwt
        // 密鑰透過環境變數取得
        // 將jwt的過期時間順便記錄到redis
        String jwt = jwtUtils.generateToken(authentication.getName());
        response.setHeader(jwtUtils.getHeader(), jwt);


        log.info("看看憑證" + authentication);
        log.info("看看 User " + authentication.getPrincipal());
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        VueData result = VueData.ok();

        //檢查是否有註冊過
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        String name = String.valueOf(oAuth2User.getAttributes().get("name"));
        String nameUnicode = UnicodeUtil.toUnicode(name);
        //如果沒有註冊過就新增一個預註冊的使用者
        if (userS.getUserByEmail(email) == null) {
            PreRegisterUserinfo preUser = userS.createAndAddPreRegisterUser(oAuth2User);
            result.setMsg("新用戶登入，請設定新密碼");
            result.setData(preUser);
        } else {
            result.setMsg("已成功登入");
            result.setData(jwt);
        }

        //驗證機制用
        Userinfo successUser = userS.getUserByAccount(authentication.getName());
        Integer userId = successUser.getUserId();
        //透過ID將驗證資訊查出來並寫入redis
        userS.getUserAuthorityInfo(userId);


//        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
//        outputStream.flush();
//        outputStream.close();
        //將JWT寫入GET參數

        String frontUrl = FRONTEND_URL_MAP.get("localhost_fake_domain");
        response.sendRedirect(frontUrl+"/200?token="+jwt+"&username="+nameUnicode);
    }
}

