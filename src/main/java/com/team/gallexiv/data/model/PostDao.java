package com.team.gallexiv.data.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {

    @Query("from Post where postTitle like %:postTitle%")
    List<Post> findByTitleLike(@Param("postTitle") String postTitle);

    List<Post> findByUserinfoByUserId(Userinfo userinfo);

    Post findByPostId(Integer postId);

    @Query("select u from Post u where u.userinfoByUserId.userId = ?1 and u.planByPlanId.planId is not null ")
    List<Post> postWithPlan(int userId);

    @Query("select u from Post u where u.userinfoByUserId.userId = ?1 and u.planByPlanId.planId is null ")
    List<Post> postWithNoPlan(int userId);
}
