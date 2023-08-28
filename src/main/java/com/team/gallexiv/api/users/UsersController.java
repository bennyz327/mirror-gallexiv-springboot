package com.team.gallexiv.api.users;

import com.team.gallexiv.model.Plan;
import com.team.gallexiv.model.UserService;
import com.team.gallexiv.model.Userinfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    final UserService UserS;

    public UsersController(UserService UserS) {
        this.UserS = UserS;
    }

    @GetMapping(path = "/userInfos/{userId}", produces = "application/json")
    @Operation(description = "取得單筆user (GET BY ID)")
    public Userinfo getUserById(@PathVariable int userId) {
        return UserS.getUserById(userId);
    }

    @PostMapping(path = "/userInfos/insert",produces = "application/json;charset=UTF-8")
    @Operation(description = "新增user")
    public Userinfo addUser( @RequestBody Userinfo user){
//        System.out.println("收到"+plan);
        return UserS.insertUser(user);
    }


    @CrossOrigin
    @GetMapping(path = "/userInfos", produces = "application/json;charset=UTF-8")
    public List<Userinfo> findAllUser() {
        List<Userinfo> result = UserS.getAllUsers();

        return result;
    }

    @Transactional
    @PutMapping(path = "/userInfos/delete")
    public String deleteUser(@RequestBody Userinfo user) {
        UserS.unableUserById(user);
        return ("刪除成功");
    }

    @Transactional
    @PutMapping(value = "/userInfos/update",consumes = "application/json")
    public String updateUser(@RequestBody Userinfo user) {
        System.out.println(user);
        UserS.updateUserById(user);
        return ("更新成功");

    }


}
