package com.team.gallexiv.data.api.Posts;

import com.team.gallexiv.data.model.*;
import com.team.gallexiv.common.lang.VueData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Tag(name = "貼文控制存取")
public class PostsController {

    private final PostService postS;

    private final UserService userS;

    private final PictureService pictureS;

    public PostsController(PostService postS, UserService userS, PictureService pictureS) {
        this.postS = postS;
        this.userS = userS;
        this.pictureS = pictureS;
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
    @GetMapping(path = "/posts", produces = "application/json;charset=UTF-8")
    @Operation(description = "取得全部筆貼文")
    public VueData findAllPost() {
        return postS.getAllPost();
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

    // 同時上傳多張圖片和貼文資料
    @PostMapping(path = "/post/upload", produces = "application/json")
    public VueData uploadPostAndFiles(
            @RequestPart("files") MultipartFile[] files,
            @RequestPart("other") Map<String, String> props) {

        //辨識請求來源
        String account = "admin";
        int userId = 1;

        log.info("上傳檔案數量: {}", files.length);
        log.info("所有檔案資訊: ");
        Collection<String> allProps = props.values();
        allProps.forEach(log::info);
        log.info("準備寫入檔案");
        pictureS.uploadPictureByUserId(userId, files);
        log.info("檔案寫入完成");

        //postS.insertPost() // TODO 更新資料庫POST資料

        return VueData.ok("上傳成功");
    }

}
