package com.team.gallexiv.data.api.Pictures;

import com.team.gallexiv.data.model.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "圖片控制存取")
public class PicturesController {
    final UserService userS;
    public PicturesController(UserService userS) {
        this.userS = userS;
    }

//    @GetMapping(path = "/users/{id}", produces = "application/json")
//    @Operation(description = "取得使用者 (GET BY ID)")
//    public Userinfo showUsersOb(@PathVariable int id) {
//        return userS.getUserById(id);
//    }
}
