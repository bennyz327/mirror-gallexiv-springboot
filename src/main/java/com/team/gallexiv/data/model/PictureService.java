package com.team.gallexiv.data.model;

import lombok.Getter;
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
import java.util.*;


import static com.team.gallexiv.common.lang.Const.*;

@Getter
@Slf4j
@Service
public class PictureService {

    private final String rootPath;

    @Autowired
    private PictureDao picD;

    @Autowired
    private PostDao postD;

    public PictureService() {

        String yamlRootPath = System.getProperty("gallexiv.upload.rootpath");
        if (yamlRootPath != null && !yamlRootPath.isEmpty()) {
            this.rootPath = yamlRootPath;
            log.info("偵測到外部設定檔，將使用 {} 作為根路徑", rootPath);
            System.out.println("yamlRootPath: " + yamlRootPath);
        } else if (System.getProperty("os.name").toLowerCase().contains("win")) {
            this.rootPath = DEFAULT_ROOTPATH_WIN;
            log.info("偵測到 Windows 系統，將使用 {} 作為根路徑", rootPath);
        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            // TODO 測試 this.rootPath = "/Users/linyucheng/Desktop/upload";
            this.rootPath = DEFAULT_ROOTPATH_MAC;
            log.info("偵測到 Mac 系統，將使用 {} 作為根路徑", rootPath);
        } else {
            this.rootPath = DEFAULT_ROOTPATH_LINUX;
            log.info("判斷為 Linux 系統，將使用 {} 作為根路徑", rootPath);
        }
        log.info("上傳路徑設定完成");
    }

    //Windows 系統適用路徑
    public void uploadPictureByUserId(Integer userId, MultipartFile[] files, String newPostIdStr) {

        //在post資料夾中以userId來建立資料夾
        String fileFolder = "/upload/post/" + userId;

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

                Path filePathObj = Paths.get(rootPath,filePath);
                log.info("單檔路徑: {}", filePathObj);
                if (!Files.exists(filePathObj)) {
                    log.info("檔案或不存在，建立新檔案");
                    Files.createDirectories(filePathObj.getParent());
                    Files.createFile(filePathObj);
                }
                Files.write(filePathObj, file.getBytes());
                log.info("圖片寫入成功");
                //每張圖片皆增加資料庫資料
                matchPicToPost(Integer.parseInt(newPostIdStr), filePath);
            }
            log.info("全部檔案寫入完成");
        } catch (IOException e) {
            log.error("無法寫入檔案: {}", e.getMessage());
        }
    }

    public void matchPicToPost(Integer postId, String filePath) {
        Status newStatus = new Status(11);
        Post matchPost = postD.findByPostId(postId);
        log.info("要放入圖片實體的POST: {}", matchPost);
        Picture newPic = new Picture(filePath, newStatus, matchPost);
        picD.save(newPic);
    }

    public List<String> getImgPathByPostId(Integer postId) {


        return picD.fintImgPathByPostId(postId);
    }

    public String getDynamicPathByPicId(Integer pictureId) {
        if (picD.findById(pictureId).isPresent()){
            return picD.findById(pictureId).get().getImgPath();
        }
        return null;
    }

}
