package com.team.gallexiv.model;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    final CommentDao commentD;
    final UserDao userinfoD;
    final StatusDao statusD;
    final PostDao postD;

    public CommentService(UserDao userinfoD, StatusDao statusD, CommentDao commentD, PostDao postD) {
        this.userinfoD = userinfoD;
        this.statusD = statusD;
        this.commentD = commentD;
        this.postD = postD;
    }

    // 取得單筆 comment
    public Comment getCommentById(int commentId) {
        Optional<Comment> comment = commentD.findById(commentId);
        return comment.orElse(null);
    }

    // 取得全部 comment
    public List<Comment> getAllComment() {
        return commentD.findAll();
    }

    // 刪除 comment
    public void deleteCommentById(Integer commentId) {
        Optional<Comment> commentOptional = commentD.findById(commentId);
        if (commentOptional.isEmpty()) {
            return;
        }
        commentD.deleteById(commentId);
    }

    // 新增 comment
    public Comment insertComment(Integer postId, Integer userId, Comment comment) {
        Optional<Post> thisPost = postD.findById(postId);
        Optional<Userinfo> thisUser = userinfoD.findById(userId);
        int thisCommentStatusId = comment.getCommentStatusByStatusId().getStatusId();
        Optional<Status> commentOptional = statusD.findById(thisCommentStatusId);

        if (thisPost.isPresent() && thisUser.isPresent() && commentOptional.isPresent()) {
            comment.setPostByPostId(thisPost.get());
            comment.setUserinfoByUserId(thisUser.get());
            comment.setCommentStatusByStatusId(commentOptional.get());
            return commentD.save(comment);
        }
        return null;
    }

    // 更新 comment
    public void updateComment(Integer commentId, String commentText) {
        Optional<Comment> commentOptional = commentD.findById(commentId);

        if (commentOptional.isEmpty()) {
            return;
        }
        Comment updateComment = commentOptional.get();
        updateComment.setCommentText(commentText);
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
