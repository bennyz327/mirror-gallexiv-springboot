package com.team.gallexiv.data.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PictureService {


    @Value("${gallexiv.upload.rootpath}")
    private String rootPath;

    @Autowired
    private PictureDao picD;

    @Autowired
    private PostDao postD;

    //Windows 系統適用路徑
    public void uploadPictureByUserId(Integer userId, MultipartFile[] files, String newPostIdStr) {
        //讀取外部變數rootpath+post作為寫入路徑前墜
        String fileFolder = rootPath + "/post";

        //在post資料夾中以userId來建立資料夾
        fileFolder = fileFolder + "/" + userId;
        //檢查資料夾是否存在，不存在則建立
        Path path = Paths.get(fileFolder);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                log.error("無法建立路徑資料夾: {}", e.getMessage());
            }
        }

        //把檔案寫入所屬的userId資料夾中，檔名以當下系統的時間格式字串命名
        try {
            List<MultipartFile> fileList = Arrays.stream(files).toList();
            for (MultipartFile file : fileList) {
                String filePath = fileFolder + "/" + new Date().getTime();
                if (file.getContentType() == null || file.getContentType().isEmpty()) {
                    log.info("此檔案無副檔名");
                } else {
                    log.info("找到檔案副檔名: {}", file.getContentType());
                    filePath = filePath + "." + file.getContentType().split("/")[1];
                }
                FileOutputStream fos = new FileOutputStream(filePath);
                fos.write(file.getBytes());
                fos.close();
                log.info("圖片寫入成功");

                //每張圖片皆增加資料庫資料
                // TODO 應該改為存相對路徑
                matchPicToPost(Integer.parseInt(newPostIdStr),filePath);
            }
            log.info("全部檔案寫入完成");
        } catch (IOException e) {
            log.error("無法寫入檔案: {}", e.getMessage());
        }
    }

    public void matchPicToPost(Integer postId, String filePath) {
        Status newStatus = new Status(11);
        Post matchPost = postD.findByPostId(postId);
        log.info("要放入圖片實體的POST: {}",matchPost);
        Picture newPic = new Picture(filePath,newStatus,matchPost);
        picD.save(newPic);
    }

}
