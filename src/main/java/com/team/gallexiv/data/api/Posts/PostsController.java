package com.team.gallexiv.data.api.Posts;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.team.gallexiv.data.dto.PostDto;
import com.team.gallexiv.data.model.Tag;
import com.team.gallexiv.data.model.*;
import com.team.gallexiv.common.lang.VueData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "貼文控制存取")
public class PostsController {

    final PostService postS;

    final UserService userS;

    final TagService tagS;

    public PostsController(PostService postS, UserService userS, TagService tagS) {
        this.postS = postS;
        this.userS = userS;
        this.tagS = tagS;
    }

    @GetMapping(path = "/posts/{id}/owner", produces = "application/json")
    @Operation(description = "取得貼文作者 (GET POST OWNER)")
    public Userinfo getPostOwner(@PathVariable int id) {
        return postS.getPostOwner(id);
    }

    @CrossOrigin
    @GetMapping(path = "/posts/post", produces = "application/json;charset=UTF-8")
    @Operation(description = "取得單筆貼文 (GET BY ID)")
    public VueData showPostForPostPage(@RequestParam Integer postId) {
        return postS.getPostById(postId);
    }

    // @CrossOrigin
    // @GetMapping(path = "/posts/{postId}", produces = "application/json")
    // @Operation(description = "取得單筆貼文 (GET BY ID)")
    // public VueData showPostsObForView(@PathVariable Integer postId) {
    // return postS.getPostById(postId);
    // }

    @CrossOrigin
    @GetMapping(path = "/posts", produces = "application/json;charset=UTF-8")
    @Operation(description = "取得全部筆貼文")
    public VueData findAllPost() {
        return postS.getAllPost();
    }

    // @PostMapping(path = "/posts/insert", produces =
    // "application/json;charset=UTF-8")
    // @Operation(description = "新增貼文")
    // public VueData addPost(@RequestBody PostDto post) {
    // log.info("進PUT");
    // log.info(Arrays.toString(post.getTagArr()));
    // log.info(post.getPostTitle());
    // log.info(post.getPostContent());
    // String[] tagArr = post.getTagArr();

    // Tag tempTag;
    // Collection<Tag> newTagC = new ArrayList<>();
    // for (String tagName : tagArr) {
    // tempTag = new Tag(tagName);
    // newTagC.add(tempTag);
    // }

    @CrossOrigin(origins = "http://172.18.135.63:3100")
    @PostMapping(path = "/posts/person", produces = "application/json;charset=UTF-8")
    public VueData findAllPersonPost() {
        String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("目前登入使用者"+accoutName);
        return postS.getPostByUserId(userS.getUserByAccount(accoutName).getUserId());
    }

    @CrossOrigin(origins = "http://localhost:3100")
    @DeleteMapping(path = "/posts/delete")
    @Operation(description = "刪除貼文")
    public VueData deletePost(@RequestParam Integer postId) {
        return postS.deletePostById(postId);
    }

    @Transactional // 少了tag跟picture
    @PutMapping("/posts/update")
    @Operation(description = "更新貼文")
    public VueData updatePost(@RequestParam Integer postId, @RequestBody Post post) {

        return postS.updatePostById(postId, post);
    }

    @GetMapping("/posts/postTitle")
    @Operation(description = "模糊查詢貼文")
    public List<Post> findPostByName(@RequestParam("postTitle") String postTitle) {
        return postS.findPostByTitleLike(postTitle);
    }

}
