package com.team.gallexiv.data.api.Posts;

import com.team.gallexiv.data.model.Post;
import com.team.gallexiv.data.model.PostService;
import com.team.gallexiv.data.model.UserService;
import com.team.gallexiv.data.model.Userinfo;
import com.team.gallexiv.common.lang.VueData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "貼文控制存取")
public class PostsController {

    final PostService postS;

    final UserService userS;

    public PostsController(PostService postS, UserService userS) {
        this.postS = postS;
        this.userS = userS;
    }

    @GetMapping(path = "/posts/{id}/owner", produces = "application/json")
    @Operation(description = "取得貼文作者 (GET POST OWNER)")
    public Userinfo getPostOwner(@PathVariable int id) {
        return postS.getPostOwner(id);
    }

    @CrossOrigin
    @GetMapping(path = "/postsById", produces = "application/json")
    @Operation(description = "取得單筆貼文 (GET BY ID)")
    public VueData showPostsOb(@RequestBody Post post) {
        return postS.getPostById(post);
    }

    @CrossOrigin
    @GetMapping(path = "/posts/post", produces = "application/json;charset=UTF-8")
    @Operation(description = "取得單筆貼文 (GET BY ID)")
    public VueData showPostForPostPage(@RequestParam Integer postId) {
        return postS.getPostByIdUseParam(postId);
    }

    @CrossOrigin
    @GetMapping(path = "/posts", produces = "application/json;charset=UTF-8")
    @Operation(description = "取得全部筆貼文")
    public VueData findAllPost() {
        return postS.getAllPost();
    }

    @PostMapping(path = "/posts/insert", produces = "application/json;charset=UTF-8")
    @Operation(description = "新增貼文")
    public VueData addPost(@RequestBody Post post) {
        return postS.insertPost(post);
    }

    @CrossOrigin(origins = "http://172.18.135.63:3100")
    @PostMapping(path = "/posts/person", produces = "application/json;charset=UTF-8")
    public VueData findAllPersonPost() {
        String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("目前登入使用者" + accoutName);
        return postS.getPostByUserId(userS.getUserByAccount(accoutName).getUserId());
    }

    @DeleteMapping(path = "/posts/delete")
    @Operation(description = "刪除貼文")
    public VueData deletePost(@RequestBody Post post) {
        return postS.deletePostById(post);
    }

    @Transactional // 少了tag跟picture
    @PutMapping("/posts/update")
    @Operation(description = "更新貼文")
    public VueData updatePost(@RequestBody Post post) {
        return postS.updatePostById(post);
    }

    @GetMapping("/posts/postTitle")
    @Operation(description = "模糊查詢貼文")
    public List<Post> findPostByName(@RequestParam("postTitle") String postTitle) {
        return postS.findPostByTitleLike(postTitle);
    }

}
