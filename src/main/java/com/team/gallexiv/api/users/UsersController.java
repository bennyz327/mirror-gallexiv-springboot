package com.team.gallexiv.api.users;

import com.team.gallexiv.model.UserService;
import com.team.gallexiv.model.Userinfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    final UserService userS;

    public UsersController(UserService UserS) {
        this.userS = UserS;
    }

    @GetMapping(path = "/userInfosById", produces = "application/json")
    @Operation(description = "取得單筆user (GET BY ID)")
    public Userinfo getUserById(@RequestBody Userinfo user) {
        return userS.getUserById(user);
    }

    @CrossOrigin
    @GetMapping(path = "/userInfos", produces = "application/json;charset=UTF-8")
    public List<Userinfo> findAllUser() {
        List<Userinfo> result = userS.getAllUsers();

        return result;
    }

    @PostMapping(path = "/userInfos/insert",produces = "application/json;charset=UTF-8")
    @Operation(description = "新增user")
    public Userinfo addUser( @RequestBody Userinfo user){
//        System.out.println("收到"+plan);
        return userS.insertUser(user);
    }

    @Transactional
    @PutMapping(path = "/userInfos/delete")
    public Userinfo deleteUser(@RequestBody Userinfo user) {
        return userS.unableUserById(user);
    }

    @Transactional
    @PutMapping(value = "/userInfos/update",consumes = "application/json")
    public String updateUser(@RequestBody Userinfo user) {
        System.out.println(user);
        userS.updateUserById(user);
        return ("更新成功");

    }


}
