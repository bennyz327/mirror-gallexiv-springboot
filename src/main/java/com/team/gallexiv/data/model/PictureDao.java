package com.team.gallexiv.data.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureDao extends JpaRepository<Picture, Integer> {

    Optional<Picture> saveImgPath(String imgPth);



}
