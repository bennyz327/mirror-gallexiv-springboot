package com.team.gallexiv.data.api.users;

import com.team.gallexiv.data.model.LinkMappingService;
import com.team.gallexiv.data.model.UserService;
import com.team.gallexiv.data.model.Userinfo;
import com.team.gallexiv.common.lang.VueData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class UsersController {

    final UserService userS;

    public UsersController(UserService UserS) {
        this.userS = UserS;
    }

    @Autowired
    private LinkMappingService linkS;

    @GetMapping(path = "/userInfos/{userId}", produces = "application/json")
    @Operation(description = "取得單筆user (GET BY ID)")
    public VueData getUserById(@PathVariable int userId) {
        String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userS.getUserById(userId);
    }

    @GetMapping(path = "/userInfos/profile", produces = "application/json")
    @Operation(description = "取得單筆user (GET BY ID)")
    public VueData getUserProfile() {
        return userS.getUserById();
    }

    @GetMapping(path = "/userInfos", produces = "application/json;charset=UTF-8")
    public VueData findAllUser() {
        return userS.getAllUsers();
    }

    @PostMapping(path = "/userInfos/insert", produces = "application/json;charset=UTF-8")
    @Operation(description = "新增user")
    public Userinfo addUser(@RequestBody Userinfo user) {
        return userS.insertUser(user);
    }

    @Transactional
    @PutMapping(path = "/userInfos/delete")
    @Operation(description = "刪除user")
    public VueData deleteUser(@RequestBody Userinfo user) {
        return userS.unableUserById(user);
    }

    @Transactional
    @PutMapping(value = "/userInfos/update", consumes = "application/json")
    public VueData updateUser(@RequestBody Userinfo user) {
//        System.out.println(user);
        return userS.updateUserById(user);

    }

    @PutMapping(value = "/userInfos/updateLinks", consumes = "application/json")
    public VueData updateUserLinks(@RequestBody Map<String, String> links) {
        log.info("更新使用者連結");
        links.forEach((k, v) -> System.out.println(k + ":" + v));
        String account = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (linkS.updateUserLinks(account, links)) {
            return VueData.ok("更新成功");
        }
        return VueData.error("更新失敗");
    }

    @PreAuthorize("not isAuthenticated()")
    @PostMapping(value = "/register", consumes = "application/json")
    public VueData registerUser(@RequestBody Map<String, String> registerInfo) {

        //TODO 生產環境清理
        log.info("請求的註冊資料");
        System.out.println(registerInfo.get("account"));
        System.out.println(registerInfo.get("password"));
        System.out.println(registerInfo.get("email"));
        System.out.println(registerInfo.get("token"));
        System.out.println(registerInfo.get("verification"));

        String token = registerInfo.get("token");
        String code = registerInfo.get("verification");

        if (userS.ifCaptchaNotNull(token, code)) {
            if (userS.ifCaptchaMatch(token, code)) {
                log.info("註冊驗證碼正確");
                if (userS.createRegisterUser(registerInfo)) {
                    log.info("註冊成功");
                    return VueData.ok("註冊成功");
                }
                log.info("電子郵件或帳號已被註冊");
                return VueData.error("電子郵件或帳號已被註冊");
            }
        }
        log.info("註冊驗證碼錯誤");
        return VueData.error("驗證失敗");
    }

    @PostMapping(value = "/auth/startVerifyMail")
    public VueData sendVerifyMail(){
//        String account = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String testAccount = "101976049684784052619";
        if(userS.sendVerifyMail(testAccount)){
            return VueData.ok("驗證信已寄出");
        }
        return VueData.error("驗證信寄送失敗");
    }

    @PostMapping(value = "/auth/verifyMail")
    public VueData verifyMail(@RequestBody Map<String, String> mailVerifyInfo){
//        String account = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String testAccount = "101976049684784052619";
        String code = mailVerifyInfo.get("code");
        if (code == null || code.isEmpty()) {
            return VueData.error("驗證碼不得為空");
        }
        if(userS.verifyMail(testAccount, code)){
            return VueData.ok("驗證成功");
        }
        return VueData.error("驗證失敗，請重新寄送驗證信");
    }
}
