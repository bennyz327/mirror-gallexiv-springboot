package com.team.gallexiv.data.api.Pictures;

import com.team.gallexiv.common.lang.VueData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(path = "/pic")
public class PictureUploadController {

    @PostMapping(path = "/upload",produces = "application/json")
    public VueData uploadPic(
            @RequestPart("files") MultipartFile[] files,
            @RequestPart("other") Map<String,String> props
    ){
        String account = "admin";
        log.info("file: {}",files.length);
        log.info("file: {}",props.get("title"));

        //把檔案寫入路徑/home/benny/1.jpg
        try {
            byte[] file = Arrays.stream(files).toList().get(0).getBytes();
            FileOutputStream fos = new FileOutputStream("/home/benny/upload/1.jpg");
            fos.write(file);
            fos.close();
        }catch (IOException e){
            log.error("error: {}",e.getMessage());
        }
        log.info("寫入完成");
        return VueData.ok();
    }
}
