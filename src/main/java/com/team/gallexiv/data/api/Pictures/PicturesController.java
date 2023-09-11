package com.team.gallexiv.data.api.Pictures;

import cn.hutool.core.io.IoUtil;
import com.team.gallexiv.data.model.Picture;
import com.team.gallexiv.data.model.PictureService;
import com.team.gallexiv.data.model.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.team.gallexiv.common.lang.Const.IMG_ROOTPATH;

@Slf4j
@RestController
@Tag(name = "圖片控制存取")
public class PicturesController {
    final UserService userS;
    final PictureService pictureS;

    public PicturesController(UserService userS, PictureService pictureS) {
        this.userS = userS;
        this.pictureS = pictureS;
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

    @GetMapping(
            value = "/test/getImage",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaTypeTest() throws IOException {
        // 指定本地文件路径
        String imagePath = "D:\\upload\\user1\\1.jpg";

        try (FileInputStream fileInputStream = new FileInputStream(new File(imagePath))) {
            // 读取文件内容并返回字节数组
            byte[] imageBytes = new byte[fileInputStream.available()];
            fileInputStream.read(imageBytes);
            return imageBytes;
        } catch (IOException e) {
            // 处理文件读取错误
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping(
            value = "/test/p/{pid}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable Integer pid) throws IOException {

        Integer userId = 1;


        // 指定本地文件路径

        String imagePath = IMG_ROOTPATH;
        imagePath = imagePath+"\\user"+userId+"\\"+pid+".jpg";

        log.info(imagePath);

        try (FileInputStream fileInputStream = new FileInputStream(new File(imagePath))) {
            // 读取文件内容并返回字节数组
            byte[] imageBytes = new byte[fileInputStream.available()];
            fileInputStream.read(imageBytes);
            return imageBytes;
        } catch (IOException e) {
            //
            log.error("例外狀況",e);
//            e.printStackTrace();
            throw e;
        }
    }


}
