package com.team.gallexiv.data.model;

import com.team.gallexiv.common.lang.VueData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    final PlanDao planD;
    final PlanForShowDao planForShowD;

    final CommentDao commentD;
    final UserDao userinfoD;
    final StatusDao statusD;
    final PostDao postD;

    public CommentService(PlanDao planD, PlanForShowDao planForShowD, UserDao userinfoD, StatusDao statusD,
            CommentDao commentD, PostDao postD) {
        this.planD = planD;
        this.planForShowD = planForShowD;
        this.userinfoD = userinfoD;
        this.statusD = statusD;
        this.commentD = commentD;
        this.postD = postD;
    }

    // 取得單筆 comment
    public VueData getCommentById(Comment comment) {
        Optional<Comment> optionalComment = commentD.findById(comment.getCommentId());
        if (optionalComment.isPresent()) {
            return VueData.ok(optionalComment.orElse(null));
        }
        return VueData.error("查詢失敗");
    }

    // 取得全部 comment
    public VueData getAllComment() {
        List<Comment> result = commentD.findAll();
        if (result.isEmpty()) {
            return VueData.error("查詢失敗");
        }
        return VueData.ok(result);
    }

    // 刪除 comment
    public VueData deleteCommentById(Comment comment) {
        Optional<Comment> optionalComment = commentD.findById(comment.getCommentId());
        if (optionalComment.isEmpty()) {
            return VueData.error("刪除失敗");
        }
        commentD.deleteById(comment.getCommentId());
        return VueData.ok("刪除成功");
    }

    // 新增 comment
    public VueData insertComment(Integer postId, Integer userId, Comment comment) {
        Optional<Post> thisPost = postD.findById(postId);
        Optional<Userinfo> thisUser = userinfoD.findById(userId);
        int thisCommentStatusId = comment.getCommentStatusByStatusId().getStatusId();
        Optional<Status> commentOptional = statusD.findById(thisCommentStatusId);

        if (thisPost.isPresent() && thisUser.isPresent() && commentOptional.isPresent()) {
            comment.setPostByPostId(thisPost.get());
            comment.setUserinfoByUserId(thisUser.get());
            comment.setCommentStatusByStatusId(commentOptional.get());
            return VueData.ok(commentD.save(comment));
        }
        return VueData.error("新增失敗");
    }

    // 更新 comment
    public VueData updateComment(Comment comment) {
        Optional<Comment> optionalComment = commentD.findById(comment.getCommentId());

        if (optionalComment.isEmpty()) {
            return VueData.error("更新失敗");
        }
        Comment updateComment = optionalComment.get();
        updateComment.setCommentText(comment.getCommentText());
        return VueData.ok(updateComment);
    }
    // public void updateComment(Integer commentId, Comment comment) {
    // Optional<Comment> commentOptional = commentD.findById(commentId);

    // if (commentOptional.isPresent()) {
    // Comment updateComment = commentOptional.get();
    // updateComment.setCommentText(updateComment.getCommentText());
    // // return commentD.save(updateComment);
    // }
    // return;

    // }
}
