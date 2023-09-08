package com.team.gallexiv.common.lang;

// 定義常數
public class Const {

    //TODO 生產環境清理
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
