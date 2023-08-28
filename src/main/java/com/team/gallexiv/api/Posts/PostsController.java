package com.team.gallexiv.api.Posts;

import com.team.gallexiv.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "貼文控制存取")
public class PostsController {

    final PostService postS;

    final UserService userS;

    @Autowired
    public PostsController(PostService postS, UserService userS) {
        this.postS = postS;
        this.userS = userS;
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

    @PostMapping(path = "/posts/insert",produces = "application/json;charset=UTF-8")
    @Operation(description = "新增貼文")
    public Post addPost(@RequestBody Post post){
        return postS.insertPost(post);
    }

    @CrossOrigin
    @GetMapping(path="/posts",produces = "application/json;charset=UTF-8")
    @Operation(description = "取得全部筆貼文")
    public List<Post> findAllPost(){
        return postS.getAllPost();
    }

    @DeleteMapping(path = "/posts/delete")
    @Operation(description = "刪除貼文")
    public String deletePost(@RequestParam Integer postId){
        if(postId != null){
            postS.deletePostById(postId);
            return ("刪除成功");
        }
        return ("刪除失敗");
    }

    @Transactional
    @PutMapping("/posts/update")
    @Operation(description = "更新貼文")
    public String updatePost(@RequestBody Post post){
        if(post != null){
            postS.updatePostById(post);
            return "更新成功";
        }
        return "更新失敗";
    }

    @GetMapping("/posts/postTitle")
    @Operation(description = "模糊查詢貼文")
    public List<Post> findPostByName(@RequestParam("postTitle") String postTitle){
        return postS.findPostByTitleLike(postTitle);
    }

}
