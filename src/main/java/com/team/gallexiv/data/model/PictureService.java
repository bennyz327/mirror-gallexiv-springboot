package com.team.gallexiv.data.model;

import org.springframework.stereotype.Service;

@Service
public class PictureService {

    final PictureDao pictureD;

    public PictureService(PictureDao pictureD){
        this.pictureD = pictureD;
    }

    public void savePicture(String imgPath){
        pictureD.saveImgPath(imgPath);
    }
}
