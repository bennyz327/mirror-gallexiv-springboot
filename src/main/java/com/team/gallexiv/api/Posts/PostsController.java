package com.team.gallexiv.api.Posts;

import com.team.gallexiv.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.*;

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

    //OK
    @CrossOrigin
    @GetMapping(path="/posts",produces = "application/json;charset=UTF-8")
    public List<Post> findAllPost(){
        List<Post> result = postS.getAllPost();
        return  result;
    }

    //OK
    @DeleteMapping(path = "/posts/delete")
    public String deletePost(@RequestParam Integer postId){
        postS.deletePostById(postId);
        return ("刪除成功");
    }


    //@RequestParam("postTime") Timestamp postTime,
    @Transactional
    @PutMapping("/posts/update")
    public String updatePost(@RequestParam int postId, @RequestParam("postTitle") String postTitle, @RequestParam("postContent") String postContent,
                           @RequestParam("postPublic") Integer postPublic,@RequestParam("postAgeLimit") Integer postAgeLimit){
        postS.updatePostById(postId,postTitle,postContent,postPublic,postAgeLimit);
        return "更新成功";
    }

    @GetMapping("/posts/postTitle")
    public List<Post> findPostByName(@RequestParam("postTitle") String postTitle){
        return postS.findPostByTitleLike(postTitle);
    }

}
