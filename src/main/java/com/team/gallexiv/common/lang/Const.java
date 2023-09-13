package com.team.gallexiv.common.lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 定義常數
public class Const {

    //TODO 生產環境清理
//    public final static String FRONTEND_URL = "http://localhost:3100";
//    public final static String FRONTEND_URL = "http://172.18.135.63:3100";
    //設定一個靜態的fRONTEND_URL_LIST 的MAP
    public final static HashMap<String,String> FRONTEND_URL_MAP = new HashMap<>() {{
        put("localhost_fake_domain", "http://gallexiv.com:3100");
        put("localhost", "http://localhost:3100");
    }};
    public final static List<String> CORS_ALLOWED_METHODS = List.of("GET", "POST", "PUT", "DELETE","OPTIONS");
    public final static List<String> CORS_ALLOWED_ORIGINS = List.of("http://gallexiv.com:3100","http://localhost:3100");
    public final static List<String> CORS_ALLOWED_HEADERS = List.of("*");
    public final static String DEFAULT_SUCCESS_MSG = "Yabai★Desune☆";
    public final static String CAPTCHA_REDIS_KEY = "captcha";
    public final static String USERNAME_PARAM = "name";
    public final static String PASSWORD_PARAM = "passwd";
    public final static String LOGIN_URI = "/login";
    public final static String LOGOUT_URI = "/logout";
    public final static String OAUTH2_LOGIN_URI = "/login/oauth2/code/**";
    public final static String JWT_HEADER = "Authorization";
    public final static Integer JWT_EXPIRE_SECONDS = 1800;
    public final static String CODE_PARAM = "code";
    public final static String CODE_TOKEN_PARAM = "token";


    public final static Integer STATUS_ON = 0;
    public final static Integer STATUS_OFF = 1;
    public static final String DEFULT_AVATAR = "https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg";
}
