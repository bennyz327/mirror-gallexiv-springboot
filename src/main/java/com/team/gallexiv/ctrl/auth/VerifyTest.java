package com.team.gallexiv.ctrl.auth;

import com.team.gallexiv.lang.VueData;
import com.team.gallexiv.model.UserService;
import com.team.gallexiv.utils.RedisUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyTest {

    final UserService userS;
    final RedisUtil redisUtil;
    public VerifyTest(UserService userS, RedisUtil redisUtil) {
        this.userS = userS;
        this.redisUtil = redisUtil;
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

}
