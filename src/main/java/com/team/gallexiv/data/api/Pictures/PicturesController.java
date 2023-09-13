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

    // @GetMapping(path = "/users/{id}", produces = "application/json")
    // @Operation(description = "取得使用者 (GET BY ID)")
    // public Userinfo showUsersOb(@PathVariable int id) {
    // return userS.getUserById(id);
    //
    // @GetMapping(path = "/test/p", produces = "application/json;charset=UTF-8")
    // public ResponseEntity<List<String>> getImageUrls(@RequestParam Integer
    // postId) {
    // List<String> imageUrls = pictureS.getImgPathByPostId(postId);
    // return ResponseEntity.ok(imageUrls);
    // }

    @GetMapping(path = "/test/p", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<byte[]>> getImagesWithMediaType(@RequestParam Integer postId) throws IOException {
        List<Picture> pictures = pictureS.getImgPathByPostId(postId);
        if (!pictures.isEmpty()) {
            List<byte[]> imageBytesList = new ArrayList<>();
            List<String> imagePaths = pictures.stream()
                    .map(Picture::getImgPath)
                    .collect(Collectors.toList());
            System.out.println("imagePaths: " + imagePaths);
            for (String imagePath : imagePaths) {
                try (FileInputStream fileInputStream = new FileInputStream(new File(imagePath))) {
                    byte[] imageBytes = new byte[fileInputStream.available()];
                    fileInputStream.read(imageBytes);
                    imageBytesList.add(imageBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return ResponseEntity.ok(imageBytesList);
        }

        return ResponseEntity.ok(Collections.emptyList());
    }

    // @GetMapping(path = "/test/p", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<List<String>> getImagesWithBase64(@RequestParam Integer
    // postId) throws IOException {
    // System.out.println("postId:" + postId);
    // List<Picture> pictures = pictureS.getImgPathByPostId(postId);
    // if (!pictures.isEmpty()) {
    // List<String> base64Images = new ArrayList<>();
    // List<String> imagePaths = pictures.stream()
    // .map(Picture::getImgPath)
    // .collect(Collectors.toList());
    // System.out.println("imagePaths: " + imagePaths);
    // for (String imagePath : imagePaths) {
    // try (FileInputStream fileInputStream = new FileInputStream(new
    // File(imagePath))) {
    // byte[] imageBytes = new byte[fileInputStream.available()];
    // fileInputStream.read(imageBytes);
    // // 将图片字节数组转换为 Base64 字符串
    // String base64Image = Base64.getEncoder().encodeToString(imageBytes);
    // base64Images.add(base64Image);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    // return ResponseEntity.ok(base64Images);
    // }

    // return ResponseEntity.ok(Collections.emptyList());
    // }

    // @GetMapping("/test/p")
    // public ResponseEntity<MultiValueMap<String, Object>>
    // getImagesAsMultipart(@RequestParam Integer postId)
    // throws IOException {
    // List<Picture> pictures = pictureS.getImgPathByPostId(postId);
    // if (!pictures.isEmpty()) {
    // MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    // for (Picture picture : pictures) {
    // String imagePath = picture.getImgPath();
    // File imageFile = new File(imagePath);
    // if (imageFile.exists()) {
    // try (FileInputStream fileInputStream = new FileInputStream(imageFile)) {
    // byte[] imageBytes = new byte[fileInputStream.available()];
    // fileInputStream.read(imageBytes);
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.IMAGE_PNG); // 根据图片类型设置对应的MediaType
    // HttpEntity<byte[]> part = new HttpEntity<>(imageBytes, headers);
    // body.add("file", part);
    // }
    // }
    // }
    // return ResponseEntity.ok()
    // .contentType(MediaType.MULTIPART_MIXED)
    // .body(body);
    // }
    // return ResponseEntity.notFound().build();
    // }

    // @GetMapping(value = "/test/p/{postId}", produces =
    // MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<List<String>> getImagesByPostId(@PathVariable Integer
    // postId) throws IOException {
    // // 在数据库中查询具有相应postId的所有图像路径
    // List<String> imagePaths = imageService.findImagePathsByPostId(postId); //
    // 假设有一个imageService来处理数据库查询

    // // 返回图像路径列表作为JSON响应
    // return ResponseEntity.ok(imagePaths);
    // }
}
