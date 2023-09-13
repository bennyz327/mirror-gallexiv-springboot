package com.team.gallexiv.data.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer> {

    // 由PostId找comments
    List<Comment> findByPostByPostIdPostId(Integer postId);

    // 由PostId找comments where status = 13 並依照時間排列
    @Query("SELECT c FROM Comment c WHERE c.postByPostId.postId = :postId AND c.commentStatusByStatusId.statusId = 13 AND c.commentByParentCommentId IS NULL ORDER BY c.commentTime DESC")
    List<Comment> findCommentIByPostIdAndStatus(@Param("postId") Integer postId);

    // 找到子留言 where status = 13
    @Query("SELECT c FROM Comment c WHERE c.commentStatusByStatusId.statusId = 13 AND c.commentByParentCommentId.commentId IS NULL ORDER BY c.commentTime DESC")
    List<Comment> findSubComment(@Param("parentCommentId") Integer parentCommentId);
}
