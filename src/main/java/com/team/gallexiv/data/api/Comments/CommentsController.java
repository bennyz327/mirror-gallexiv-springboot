package com.team.gallexiv.data.api.Comments;

import com.team.gallexiv.data.model.CommentService;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.model.Comment;
import com.team.gallexiv.data.model.UserService;
import jakarta.transaction.Transactional;
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
    @PutMapping(path = "comments/update")
    public VueData updateCommentForAdmin(@RequestParam Integer commentId, String commentText) {
        return commentS.updateComment(commentId, commentText);
    }

    // ------------給一般user使用---------------//

    // 由PostId找comments
    @GetMapping(path = "comments/findByPostId", produces = "application/json;charset=UTF-8")
    public VueData getCommentsByPostId(@RequestParam Integer postId) {
        return commentS.getCommentsByPostId(postId);
    }

    // 新增 comment
    @PostMapping(path = "/comments/insert", produces = "application/json;charset=UTF-8")
    // public VueData addComment(@RequestBody CommentDto commentDto, HttpSession
    // httpSession) {
    // httpSession.getAttribute();
    public VueData addComment(@RequestBody CommentDto commentDto) {
        Integer userId = 2;
        return commentS.insertComment(userId, commentDto.getPostId(), commentDto.getCommentText(),
                commentDto.getParentCommentId());
    }

}
