package com.team.gallexiv.security.ctrl;

import cn.hutool.core.map.MapUtil;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

    @GetMapping("/test/pass")public VueData passEncode() {   // 密码加密
        //模擬註冊
        // TODO 資料庫密碼型態改變
        String pass = bCryptPasswordEncoder.encode("111111");      // 密码验证
        boolean matches = bCryptPasswordEncoder.matches("111111", pass);
        return VueData.ok(
                MapUtil.builder()
                        .put("pass", pass)
                        .put("marches", matches)
                        .build()
        );
    }

}
