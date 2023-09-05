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
        if (mf.isEmpty()) {
            return "File is empty";
        }

        String fileName = mf.getOriginalFilename();
        System.out.println("fileName:" + fileName);

        // 创建上传目录（如果不存在）
        File uploadDir = new File("C:/upload");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 生成完整的文件路径
        assert fileName != null;
        File saveFilePath = new File(uploadDir, fileName);

        // 保存文件到磁盘
        mf.transferTo(saveFilePath);

        if (!fileName.isEmpty()) {
            // 创建一个 Picture 对象并设置 imgPath
            Picture picture = new Picture();
            picture.setImgPath(saveFilePath.getAbsolutePath());

            // 保存图片信息到数据库
            savePicture(picture);
        }

        return "saveFilePath:" + saveFilePath;
    }

    private void savePicture(Picture picture) {
        pictureS.savePicture(picture);
    }

}
