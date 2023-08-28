package com.team.gallexiv.api.Comments;

import com.team.gallexiv.model.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentsController {

    final CommentService commentS;
    final UserService userS;

    public CommentsController(CommentService commentS, UserService userS) {
        this.commentS = commentS;
        this.userS = userS;
    }

    // 取得單筆 comment by id
    @GetMapping(path = "/comments/{commentId}", produces = "application/json;charset=UTF-8")
    public Comment getCommentById(@PathVariable Integer commentId) {
        return commentS.getCommentById(commentId);
    }

    // 取得全部 comment
    @CrossOrigin
    @GetMapping(path = "/comments", produces = "application/json;charset=UTF-8")
    public List<Comment> getAllComment() {
        List<Comment> result = commentS.getAllComment();
        return result;
    }

    // 刪除 comment
    @DeleteMapping(path = "comments/delete")
    public String deleteComment(@RequestParam Integer commentId) {
        commentS.deleteCommentById(commentId);
        return "ok";
    }

    // 新增 comment
    @PostMapping(path = "/comments/insert", produces = "application/json;charset=UTF-8")
    public Comment addComment(@RequestBody Comment comment) {
        Integer postId = comment.getPostByPostId().getPostId();
        Integer userId = comment.getUserinfoByUserId().getUserId();
        return commentS.insertComment(postId, userId, comment);
    }

    // 更新 comment
    @Transactional
    @PutMapping(path = "comments/update")
    public String updateComment(@RequestParam Integer commentId,
            @RequestParam("commentText") String commentText) {
        commentS.updateComment(commentId, commentText);
        return "ok";
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
