package com.team.gallexiv.api;

import com.team.gallexiv.lang.VueData;
import com.team.gallexiv.model.UserService;
import com.team.gallexiv.model.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VerifyTest {

    final
    UserService userS;
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
}
