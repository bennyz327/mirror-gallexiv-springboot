package com.team.gallexiv.data.model;

import com.jhlabs.math.VLNoise;
import com.team.gallexiv.common.lang.VueData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
// import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // ------------給admin使用---------------//
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
    // -----------------------------------//

    // 刪除 comment (更改 status = 14)(admin 和一般 user 通用)
    @Transactional
    public VueData deleteCommentById(Integer commentId) {
        try {
            Optional<Comment> thisComment = commentD.findById(commentId);
            Integer thisCommentStatusId = 14;
            Optional<Status> thisCommentStatus = statusD.findById(thisCommentStatusId);
            if (thisComment != null) {
                Comment deleteComment = thisComment.get();
                deleteComment.setCommentStatusByStatusId(thisCommentStatus.get());
                commentD.save(deleteComment);
            }
            return VueData.ok("刪除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return VueData.error("刪除失敗");
        }

    }

    // 新增 comment
    @Transactional
    public VueData insertComment(Integer userId, Integer postId, String commentText, Integer parentCommentId) {
        try {
            Comment comment = new Comment();
            Optional<Post> thisPost = postD.findById(postId);
            Optional<Userinfo> thisUser = userinfoD.findByUserId(userId);
            Integer thisCommentStatusId = 13;
            Optional<Status> thisCommentStatus = statusD.findById(thisCommentStatusId);

            comment.setPostByPostId(thisPost.get());
            comment.setUserinfoByUserId(thisUser.get());
            comment.setCommentText(commentText);
            comment.setCommentStatusByStatusId(thisCommentStatus.get());

            if (parentCommentId != null) {
                Optional<Comment> parentComment = commentD.findById(parentCommentId);
                comment.setCommentByParentCommentId(parentComment.get());
            } else {
                comment.setCommentByParentCommentId(null);
            }
            return VueData.ok(commentD.save(comment));

        } catch (Exception e) {
            e.printStackTrace();
            return VueData.error("新增失敗");
        }
    }

    // 更新 comment
    @Transactional
    public VueData updateComment(Integer commentId, String commentText) {
        try {
            Optional<Comment> thisCommentId = commentD.findById(commentId);
            if (thisCommentId != null) {
                Comment updateComment = thisCommentId.get();
                updateComment.setCommentText(commentText);
                commentD.save(updateComment);
            }
            return VueData.ok("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return VueData.error("更新失敗");
        }

    }

    // ------------給一般user使用---------------//

    // 由 PostId 找 comments
    public VueData getCommentsByPostId(Integer postId) {
        List<Comment> comments = commentD.findCommentIByPostIdAndStatus(postId);
        if (!comments.isEmpty()) {
            return VueData.ok(comments);
        }
        return VueData.error("查詢失敗");
    }

    // 找到子留言
    public VueData getSubComment(Integer parentCommentId) {
        List<Comment> subComments = commentD.findSubComment(parentCommentId);
        if (!subComments.isEmpty()) {
            return VueData.ok(subComments);
        }
        return VueData.error("查詢失敗");
    }

}
