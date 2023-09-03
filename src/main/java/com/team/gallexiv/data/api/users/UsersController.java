package com.team.gallexiv.data.api.users;

import com.team.gallexiv.data.model.UserService;
import com.team.gallexiv.data.model.Userinfo;
import com.team.gallexiv.common.lang.VueData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
public class UsersController {

    final UserService userS;

    public UsersController(UserService UserS) {
        this.userS = UserS;
    }

//    @GetMapping(path = "/userInfosById", produces = "application/json")
//    @Operation(description = "取得單筆user (GET BY ID)")
//    public VueData getUserById(@RequestBody Userinfo user) {
//        System.out.println("test");
//        return userS.getUserById(user);
//    }

    @PostMapping(path = "/userInfosById")
    public VueData getUserById(@RequestBody Userinfo user) {
        System.out.println("test");
        return userS.getUserById(user);
    }

//    @CrossOrigin
    @GetMapping(path = "/userInfos", produces = "application/json;charset=UTF-8")
    public VueData findAllUser() {
        return userS.getAllUsers();
    }

    @PostMapping(path = "/userInfos/insert", produces = "application/json;charset=UTF-8")
    @Operation(description = "新增user")
    public Userinfo addUser(@RequestBody Userinfo user) {
        // System.out.println("收到"+plan);
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
        System.out.println(user);
        return userS.updateUserById(user);

    }

}
