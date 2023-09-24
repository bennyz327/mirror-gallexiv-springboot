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

    @Query("SELECT p FROM Post p WHERE p.postStatusByStatusId.statusId = 7 AND p.postPublic = 0 AND p.planByPlanId.planId is null ORDER BY postTime DESC")
    List<Post> findAllPostByStatus();

    @Query("SELECT p FROM Post p WHERE p.userinfoByUserId.userId = :userId AND p.postStatusByStatusId.statusId = 7 ORDER BY postTime DESC")
    List<Post> findUserPostsByStatus(@Param("userId") Integer userId);

    @Query("select p from Post p where p.userinfoByUserId.userId = ?1 and p.planByPlanId.planId is not null and p.postStatusByStatusId.statusId = 7 ORDER BY postTime DESC")
    List<Post> postWithPlan(int userId);

    @Query("select p from Post p where p.userinfoByUserId.userId = ?1 and p.planByPlanId.planId is null and p.postStatusByStatusId.statusId = 7 ORDER BY postTime DESC")
    List<Post> postWithNoPlan(int userId);

    @Query("SELECT p FROM Post p JOIN p.tagsByPostId t WHERE t.tagName = ?1")
    List<Post> postWithTagName(String tagName);
}
