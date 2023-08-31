package com.team.gallexiv.data.api.Comments;

import com.team.gallexiv.lang.VueData;
import com.team.gallexiv.model.*;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.team.gallexiv.data.model.CommentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
// @RequestMapping(path = { "/pages/ajax" })
public class CommentsController {

    final CommentService commentS;
    final UserService userS;

    public CommentsController(CommentService commentS, UserService userS) {
        this.commentS = commentS;
        this.userS = userS;
    }

    // 取得單筆 comment by id
    @GetMapping(path = "/comments/findById", produces = "application/json;charset=UTF-8")
    public VueData getCommentById(@RequestBody Comment comment) {
        return commentS.getCommentById(comment);
    }

    // 取得全部 comment
    // @GetMapping(path = "/comments/findAll", produces =
    // "application/json;charset=UTF-8")
    // public Map<String, List<Comment>> getAllComment() {
    // List<Comment> result = commentS.getAllComment();
    // Map<String, List<Comment>> response = new HashMap<>();
    // response.put("list", result);
    // return response;
    // }
    @GetMapping(path = "/comments/findAll", produces = "application/json;charset=UTF-8")
    public VueData getAllComment() {
        commentS.getAllComment();
        return commentS.getAllComment();
    }

    // 刪除 comment
    @DeleteMapping(path = "comments/delete")
    public VueData deleteComment(@RequestBody Comment comment) {
        return commentS.deleteCommentById(comment);
    }

    // 新增 comment
    @PostMapping(path = "/comments/insert", produces = "application/json;charset=UTF-8")
    public VueData addComment(@RequestBody Comment comment) {
        Integer postId = comment.getPostByPostId().getPostId();
        Integer userId = comment.getUserinfoByUserId().getUserId();
        return commentS.insertComment(postId, userId, comment);
    }

    // 更新 comment
    @Transactional
    @PutMapping(path = "comments/update")
    public VueData updateComment(@RequestBody Comment comment) {
        return commentS.updateComment(comment);
    }
    // @Transactional
    // @PutMapping(path = "comments/update/{commentId}", produces =
    // "application/json;charset=UTF-8")
    // public String updateComment(@PathVariable Integer commentId, @RequestBody
    // Comment comment) {
    // commentS.insertComment(comment);
    // return "ok";
    // }

    // @PutMapping(path = "comments/update")
    // public String updateComment() {
    // return "ok";
    // }

}
