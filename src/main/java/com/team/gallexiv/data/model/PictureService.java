package com.team.gallexiv.data.model;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PictureService {

    final PictureDao pictureD;

    public PictureService(PictureDao pictureD){
        this.pictureD = pictureD;
    }

    public void savePicture(Picture picture){
        pictureD.save(picture);
    }
}
