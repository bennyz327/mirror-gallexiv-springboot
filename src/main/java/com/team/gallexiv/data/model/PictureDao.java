package com.team.gallexiv.data.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureDao extends JpaRepository<Picture, Integer> {

    List<Picture> findByPostByPostIdPostId(Integer postId);

    @Query("SELECT p FROM Picture p WHERE p.postByPostId.postId= :postId")
    List<Picture> fintImgPathByPostId(Integer postId);

}
