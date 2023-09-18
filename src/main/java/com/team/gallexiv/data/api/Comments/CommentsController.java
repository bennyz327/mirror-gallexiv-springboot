package com.team.gallexiv.data.api.Comments;

import com.team.gallexiv.data.model.CommentService;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.dto.CommentDto;
import com.team.gallexiv.data.dto.SubCommentDto;
import com.team.gallexiv.data.model.Comment;
import com.team.gallexiv.data.model.UserService;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(path = "/comments/findAll", produces = "application/json;charset=UTF-8")
    public VueData getAllComment() {
        commentS.getAllComment();
        return commentS.getAllComment();
    }

    // 刪除 comment (admin 和一般 user 通用)
    @DeleteMapping(path = "comments/delete")
    public VueData deleteComment(@RequestParam Integer commentId) {
        return commentS.deleteCommentById(commentId);
    }

    // 更新 comment
    @Transactional
    @PutMapping(path = "comments/update")
    public VueData updateComment(@RequestParam Integer commentId, @RequestParam String commentText) {
        System.out.println("commentId:" + commentId + "---------------" + "commentText:" + commentText);
        return commentS.updateComment(commentId, commentText);
    }

    // ------------給一般user使用---------------//

    // 由PostId找comments
    @GetMapping(path = "comments/findByPostId", produces = "application/json;charset=UTF-8")
    public VueData getCommentsByPostId(@RequestParam Integer postId) {
        return commentS.getCommentsByPostId(postId);
    }

    // 由postId找subComments
    @GetMapping(path = "comments/findSubByPostId", produces = "application/json;charset=UTF-8")
    public VueData getSubCommentsByPostId(@RequestParam Integer postId) {
        return commentS.getSubComment(postId);
    }

    // 由Dto找subComments
    @GetMapping(path = "comments/findSubByPostIdDto", produces = "application/json;charset=UTF-8")
    public VueData getSubComments(@RequestParam int postId) {
        return commentS.getSubCommentDto(postId);

    }

    // 新增 comment
    @PostMapping(path = "/comments/insert", produces = "application/json;charset=UTF-8")
    public VueData addComment(@RequestBody CommentDto commentDto) {
        // String accoutName = (String)
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Integer userId = userS.getUserByAccount(accoutName).getUserId();
        // System.out.println("userId:" + userId);
        return commentS.insertComment(commentDto.getPostId(), commentDto.getCommentText(),
                commentDto.getParentCommentId());
    }

}
