package com.team.gallexiv.data.api.Pictures;

import com.team.gallexiv.data.model.Picture;
import com.team.gallexiv.data.model.PictureService;
import com.team.gallexiv.data.model.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Tag(name = "圖片控制存取")
public class PicturesController {
    final UserService userS;
    final PictureService pictureS;

    public PicturesController(UserService userS,PictureService pictureS) {
        this.userS = userS;
        this.pictureS =pictureS;
    }

    @PostMapping(path = "/uploadPicture", produces = "application/json;charset=UTF-8")
    public String processAction(@RequestParam("myFiles") MultipartFile mf) throws IOException {
        String fileName = mf.getOriginalFilename();
        System.out.println("fileName:" + fileName);

        String saveFileDir = "c:/temp/upload/";
        //完整的路徑
        assert fileName != null;
        File saveFilePath = new File(saveFileDir,fileName);

        byte[] b1 = mf.getBytes();
        mf.transferTo(saveFilePath);

        if(fileName.length()!=0) {
            savePicture(fileName);
        }

        return "saveFilePath:" + saveFilePath;
    }

    private void savePicture(String imgPath) {
        pictureS.savePicture(imgPath);
    }


}
