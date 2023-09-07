package com.team.gallexiv.data.api.auth;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.model.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Map;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static io.jsonwebtoken.security.Keys.secretKeyFor;

@RestController()
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
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String name = "隨便打";
        return VueData.ok("你的名稱為"+name);
    }

    @GetMapping("/test/pass")
//    @PreAuthorize(hasRole("allPermission"))
    public VueData passEncode(@RequestParam String pass) {   // 密码加密

        //模擬資料庫密碼加密結果
        // TODO 資料庫-密碼型態改變
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

    @GetMapping("/test/key")
    public VueData testKey() {
        // 測試結果：用相同的btye[]的密鑰是一樣的
        // 但是byte[]和String轉換後的byte[]不一樣
        // 所以儲存到資料庫的密鑰要用byte[]儲存

        SecretKey key1 = secretKeyFor(SignatureAlgorithm.HS512);
        byte[] key1byte = key1.getEncoded();
        Integer len = key1byte.length;

        SecretKey key2 = hmacShaKeyFor(key1byte);
        byte[] key2byte = key2.getEncoded();

        String key2String = new String(key2byte);
        byte[] key2StringToByte = key2String.getBytes();
        Integer stringLen = key2String.length();

        boolean ifStringByteMatches = Arrays.equals(key2byte, key2StringToByte);
        boolean ifKeyMatches = key1.equals(key2);
        boolean ifByteMatches = Arrays.equals(key1byte, key2byte);

        Map map = MapBuilder.create()
                .put("ifKeyMatches ", ifKeyMatches)
                .put("ifByteMatches ", ifByteMatches)
                .put("ifStringByteMatches ", ifStringByteMatches)
                .put("byte len ", len)
                .put("string len ", stringLen)
                .build();
        return VueData.ok("test", map);
    }

    @GetMapping("/test/redisDelete")
    public VueData redisDelete(@RequestParam String key) {
        redisUtil.del(key);
        return VueData.ok("已發送刪除指令");
    }
}
