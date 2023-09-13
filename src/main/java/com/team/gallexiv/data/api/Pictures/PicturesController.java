package com.team.gallexiv.data.api.Pictures;

import com.team.gallexiv.data.model.Picture;
import com.team.gallexiv.data.model.PictureService;
import com.team.gallexiv.data.model.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Tag(name = "圖片控制存取")
public class PicturesController {

    @Value("${gallexiv.upload.rootpath}")
    private String rootPath;
    final UserService userS;
    final PictureService pictureS;

    public PicturesController(UserService userS, PictureService pictureS) {
        this.userS = userS;
        this.pictureS = pictureS;
    }

    @GetMapping(value = "/test/p")
    public ResponseEntity<List<String>> getImagesWithMediaType(@RequestParam Integer postId) throws IOException {
        List<String> pictures = pictureS.getImgPathByPostId(postId);
        List<String> imageUrls = new ArrayList<>();

        for (String picture : pictures) {
            try (FileInputStream fileInputStream = new FileInputStream(new File(picture))) {
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
