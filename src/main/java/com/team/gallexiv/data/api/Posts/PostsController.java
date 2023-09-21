package com.team.gallexiv.data.api.Posts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.team.gallexiv.data.model.*;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.team.gallexiv.data.dto.PostDto;
import com.team.gallexiv.data.model.*;
import com.team.gallexiv.common.lang.VueData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.*;

@Slf4j
@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "貼文控制存取")
public class PostsController {

    private final PostService postS;

    private final UserService userS;

    private final PictureService pictureS;

    private final TagService tagS;

    public PostsController(PostService postS, UserService userS, PictureService pictureS, TagService tagS) {
        this.postS = postS;
        this.userS = userS;
        this.pictureS = pictureS;
        this.tagS = tagS;
    }

    @GetMapping(path = "/posts/{id}/owner", produces = "application/json")
    @Operation(description = "取得貼文作者 (GET POST OWNER)")
    public Userinfo getPostOwner(@PathVariable int id) {
        return postS.getPostOwner(id);
    }

    @CrossOrigin
    @GetMapping(path = "/posts/{postId}", produces = "application/json")
    @Operation(description = "取得單筆貼文 (GET BY ID)")
    public VueData showPostsOb(@PathVariable Integer postId) {
        return postS.getPostById(postId);
    }

    @CrossOrigin
    @GetMapping(path = "/posts/post", produces = "application/json;charset=UTF-8")
    @Operation(description = "取得單筆貼文 (GET BY ID)")
    public VueData showPostForPostPage(@RequestParam Integer postId) {
        return postS.getPostByIdUseParam(postId);
    }

    @CrossOrigin
    @GetMapping(path = "/posts", produces = "application/json;charset=UTF-8")
    @Operation(description = "取得作者全部筆貼文")
    public VueData findUserAllPost() {
        return postS.getUserAllPost();
    }

    @CrossOrigin(origins = "http://172.18.135.63:3100")
    @PostMapping(path = "/posts/person", produces = "application/json;charset=UTF-8")
    public VueData findAllPersonPost() {
        // 從JWT解析請求者
        String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("目前登入使用者" + accoutName);
        return postS.getPostByUserId(userS.getUserByAccount(accoutName).getUserId());
    }

    @DeleteMapping(path = "/posts/{postId}/delete")
    @Operation(description = "刪除貼文")
    public VueData deletePost(@PathVariable Integer postId) {
        return postS.deletePostById(postId);
    }

    // @Transactional // 少了tag跟picture
    // @PutMapping("/posts/update")
    // @Operation(description = "更新貼文")
    // public VueData updatePost(@RequestBody Post post) {
    // log.info("進PUT");
    // log.info(post.toString());
    // return postS.updatePostById(post);
    // }
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

    // 同時上傳多張圖片和貼文資料
    @PostMapping(path = "/post/upload", produces = "application/json")
    @Operation(description = "新增貼文")
    public VueData uploadPostAndFiles(
            @RequestPart("files") MultipartFile[] files,
            @RequestPart("other") Map<String, String> props) throws JsonProcessingException {

        // 辨識請求來源
        String account = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("目前登入使用者: {}", account);
        Userinfo optionalUserinfo = userS.getUserByAccount(account);
        if (optionalUserinfo == null) {
            return VueData.error("token無效，請先登入");
        }
        int userId = optionalUserinfo.getUserId();
        log.info("ID: {}", userId);

        log.info("Post 資料 準備新增");
        // 資料庫塞POST資料
        String newPostIdStr = postS.insertPost(props);
        log.info("Post 資料 新增完成");

        // 儲存到檔案系統，資料庫塞圖片資料
        log.info("上傳檔案數量: {}", files.length);
        log.info("所有檔案資訊: ");
        Collection<String> allProps = props.values();
        allProps.forEach(log::info);
        log.info("準備寫入檔案");
        // 給內部 使用者ID、檔案、新圖片ID字串
        log.info("圖片ID字串 {}", newPostIdStr);
        pictureS.uploadPictureByUserId(userId, files, newPostIdStr);

        return VueData.ok("上傳成功");
    }

    @CrossOrigin
    @GetMapping(path = "/posts", produces = "application/json;charset=UTF-8")
    @Operation(description = "取得全部筆貼文")
    public VueData findAllPost(@RequestParam(required = false, defaultValue = "0") int p,
            @RequestParam int s,
            @RequestParam(required = false, defaultValue = "0") int userId) {
        if (s == 0) {
            return postS.findPostWithPlan();
        }
        if (s == 1) {
            return postS.findPostNotWithPlan();
        }
        if (s == 2) {
            return postS.getAllPost();
        }
        // 別人的免費
        if (s == 3) {
            if (userId == 0) {
                return null;
            }
            return postS.findOtherUserPost(userId, s);
        }
        // 別人的付費
        if (s == 4) {
            if (userId == 0) {
                return null;
            }
            return postS.findOtherUserPost(userId, s);
        }
        return null;
    }

    @GetMapping("/posts/search")
    public VueData SearchPostWithTagName(@RequestParam String tagName) {
        return postS.findPostByTagName(tagName);
    }

}
