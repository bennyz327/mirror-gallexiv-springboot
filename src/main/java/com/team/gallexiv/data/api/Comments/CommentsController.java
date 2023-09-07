package com.team.gallexiv.data.api.Comments;

import com.team.gallexiv.data.model.CommentService;
import com.team.gallexiv.data.model.Post;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.dto.CommentDto;
import com.team.gallexiv.data.model.Comment;
import com.team.gallexiv.data.model.UserService;
import com.team.gallexiv.data.model.Userinfo;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
// @RequestMapping(path = { "/pages/ajax" })
public class CommentsController {

    final CommentService commentS;
    final UserService userS;

    public CommentsController(CommentService commentS, UserService userS) {
        this.commentS = commentS;
        this.userS = userS;
    }

    // ------------給admin使用---------------//
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

    // 刪除 comment
    @DeleteMapping(path = "comments/delete")
    public VueData deleteComment(@RequestBody Comment comment) {
        return commentS.deleteCommentById(comment);
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

    // 更新 comment
    @Transactional
    @PutMapping(path = "comments/update")
    public VueData updateComment(@RequestBody Comment comment) {
        return commentS.updateComment(comment);
    }

    // -------------------------------//

    // ------------給一般user使用---------------//

    // 由PostId找comments
    @GetMapping(path = "comments/findByPostId", produces = "application/json;charset=UTF-8")
    public VueData getCommentsByPostId(@RequestParam int postId) {
        return commentS.getCommentsByPostId(postId);
    }

    // 新增 comment
    // @PostMapping(path = "/comments/forUserInsert", produces =
    // "application/json;charset=UTF-8")
    // public String addCommentByU(@RequestBody Comment comment) {
    // Integer postId = 1;
    // Integer userId = 3;
    // return commentS.insertCommentByUser(postId, userId, comment);
    // }

}
