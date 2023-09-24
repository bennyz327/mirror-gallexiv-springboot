package com.team.gallexiv.data.api.Pictures;

import cn.hutool.core.io.IoUtil;
import com.team.gallexiv.data.model.*;
import com.team.gallexiv.data.model.PictureService;
import com.team.gallexiv.data.model.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import static com.team.gallexiv.common.lang.Const.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/p")
@Tag(name = "圖片控制存取")
public class PicturesController {

    private String rootPath;
    final UserService userS;
    final PictureService pictureS;

    final UserDao userD;

    public PicturesController(UserService userS, PictureService pictureS, UserDao userD) {
        this.userS = userS;
        this.pictureS = pictureS;
        this.userD = userD;
    }

    @GetMapping(value = "/test/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
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

    @GetMapping(value = "/test/{pid}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable Integer pid) throws IOException {

        String accountName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // if ((!Objects.equals(accountName, "anonymousUser"))) {
        // Optional<Userinfo> thisUser = userD.findByAccount(accountName);
        // //TODO 檢查圖片訪問限制
        // log.info("檢查圖片訪問限制");
        // int userId = thisUser.get().getUserId();
        // }

        String imagePath;

        log.info(System.getProperty("os.name").toLowerCase());

        imagePath = pictureS.getRootPath() + pictureS.getDynamicPathByPicId(pid);
        Path imgPath = Paths.get(imagePath);
        log.info("圖片路徑: {}", imagePath);
        if (!Files.exists(imgPath)) {
            log.info("圖片不存在");
        }

        try (FileInputStream fileInputStream = new FileInputStream(imagePath)) {
            byte[] imageBytes = new byte[fileInputStream.available()];
            fileInputStream.read(imageBytes);
            return imageBytes;
        } catch (IOException e) {
            log.error("例外狀況", e);
            throw e;
        }
    }

    @GetMapping(value = "/test/pic")
    public ResponseEntity<List<String>> getImagesWithMediaType(@RequestParam Integer postId) throws IOException {
        List<String> pictures = pictureS.getImgPathByPostId(postId);
        List<String> imageUrls = new ArrayList<>();

        for (String picture : pictures) {
            String imgpath = pictureS.getRootPath() + picture;
            try (FileInputStream fileInputStream = new FileInputStream(new File(imgpath))) {
                byte[] imageBytes = new byte[fileInputStream.available()];
                fileInputStream.read(imageBytes);
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                String imageUrl = "data:image/png;base64," + base64Image;
                imageUrls.add(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return ResponseEntity.ok(imageUrls);
    }

    private String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex != -1) {
            return filePath.substring(lastDotIndex + 1);
        }
        return "png"; // 如果无法从文件路径中提取扩展名，则默认为 PNG 格式
    }

}
