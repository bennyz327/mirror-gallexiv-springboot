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
    List<Comment> findByPostByPostIdPostId(int postId);

}
