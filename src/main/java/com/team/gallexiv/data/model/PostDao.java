package com.team.gallexiv.data.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {

    @Query("from Post where postTitle like %:postTitle%")
    List<Post> findByTitleLike(@Param("postTitle") String postTitle);

    List<Post> findByUserinfoByUserId(Userinfo userinfo);
}
