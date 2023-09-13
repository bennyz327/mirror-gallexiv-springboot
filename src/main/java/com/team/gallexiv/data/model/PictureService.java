package com.team.gallexiv.data.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PictureService {

    final PictureDao pictureD;

    @Autowired
    public PictureService(PictureDao pictureD) {
        this.pictureD = pictureD;
    }

    public void savePicture(Picture picture) {
        pictureD.save(picture);
    }

    public List<Picture> getImgPathByPostId(Integer postId) {
        return pictureD.fintImgPathByPostId(postId);
    }
}