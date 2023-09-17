package com.team.gallexiv.data.model;

import com.team.gallexiv.common.lang.VueData;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 刪除 comment (更改 status = 14)(admin 和一般 user 通用)
    public VueData deleteCommentById(Integer commentId) {
        try {
            String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Userinfo> thisUser = userinfoD.findByAccount(accoutName);
            Integer thisUserId = thisUser.get().getUserId();

            Optional<Comment> thisComment = commentD.findById(commentId);
            Integer thisCommentUserId = thisComment.get().getUserinfoByUserId().getUserId();

            Integer thisCommentStatusId = 14;
            Optional<Status> thisCommentStatus = statusD.findById(thisCommentStatusId);
            if (thisComment != null && (thisUserId == thisCommentUserId)) {
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

    // 新增 comment (admin 和一般 user 通用)
    public VueData insertComment(Integer postId, String commentText, Integer parentCommentId) {
        try {
            String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Comment comment = new Comment();
            Optional<Post> thisPost = postD.findById(postId);
            Optional<Userinfo> thisUser = userinfoD.findByAccount(accoutName);
            System.out.println(thisUser);
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
    public VueData updateComment(Integer commentId, String commentText) {
        try {
            String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Userinfo> thisUser = userinfoD.findByAccount(accoutName);
            Integer thisUserId = thisUser.get().getUserId();

            Optional<Comment> thisCommentId = commentD.findById(commentId);
            Optional<Comment> thisComment = commentD.findById(commentId);
            Integer thisCommentUserId = thisComment.get().getUserinfoByUserId().getUserId();

            if (thisCommentId != null && (thisUserId == thisCommentUserId)) {
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
