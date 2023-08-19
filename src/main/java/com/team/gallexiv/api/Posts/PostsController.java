package com.team.gallexiv.api.Posts;

import com.team.gallexiv.model.Post;
import com.team.gallexiv.model.PostService;
import com.team.gallexiv.model.Userinfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "貼文控制存取")
public class PostsController {

    final PostService postS;

    @Autowired
    public PostsController(PostService postS) {
        this.postS = postS;
    }

    @CrossOrigin
    @GetMapping(path = "/posts/{id}", produces = "application/json")
    @Operation(description = "取得單筆貼文 (GET BY ID)")
    public Post showPostsOb(@PathVariable int id) {
        return postS.getPostById(id);
    }

    @GetMapping(path = "/posts/{id}/owner", produces = "application/json")
    @Operation(description = "取得貼文作者 (GET POST OWNER)")
    public Userinfo getPostOwner(@PathVariable int id){
        return postS.getPostOwner(id);
    }

}
