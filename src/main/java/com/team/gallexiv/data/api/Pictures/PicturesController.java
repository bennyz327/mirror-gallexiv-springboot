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

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/p")
@Tag(name = "圖片控制存取")
public class PicturesController {

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
            value = "/test/{pid}",
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
