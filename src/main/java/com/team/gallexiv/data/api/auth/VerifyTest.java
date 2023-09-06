package com.team.gallexiv.data.api.auth;

import cn.hutool.core.map.MapUtil;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.team.gallexiv.common.lang.Const.API_VERSION_URI;

@RestController(API_VERSION_URI)
public class VerifyTest extends BaseController {

    private final UserService userS;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public VerifyTest(UserService userS) {
        this.userS = userS;
    }

    @GetMapping("/test/admin")
    public String admin() {
        return "admin OK";
    }
    @GetMapping("/test/user")
    public String user() {
        return "user OK";
    }

    //包裝測試
    @GetMapping("/test/userall")
    public VueData findAllUser() {
        return VueData.ok(userS.getAllUsers());
    }

    //redis測試
    @GetMapping("/test/redis")
    public VueData redisTest() {
        redisUtil.set("redis", "这是redis的测试数据");
        Object redis = redisUtil.get("redis");
        return VueData.ok(redis.toString());
    }

    @GetMapping("/auth/iflogin")
    public VueData testIfLogin() {
        return VueData.ok("已登入");
    }

    @GetMapping("/auth/getmyinfo")
    public VueData testGetMyInfo() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return VueData.ok("你的名稱為"+name);
    }

    @GetMapping("/test/pass")
    public VueData passEncode(@RequestParam String pass) {   // 密码加密

        //模擬資料庫密碼加密結果 TODO 資料庫密碼型態改變
        //測試用 幫密碼加密
        String encode_pass = bCryptPasswordEncoder.encode(pass);
        //自己比對一次密碼
        boolean ifmatches = bCryptPasswordEncoder.matches(pass, encode_pass);
        return VueData.ok(
                MapUtil.builder()
                        .put("pass", encode_pass)
                        .put("marches", ifmatches)
                        .build()
        );
    }

}
