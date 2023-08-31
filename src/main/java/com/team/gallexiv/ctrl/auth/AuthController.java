package com.team.gallexiv.ctrl.auth;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.team.gallexiv.lang.VueData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@CrossOrigin(origins = "http://localhost:3100", maxAge = 3600)
public class AuthController extends BaseController {

    @Autowired
    Producer producer;

    @GetMapping("/captcha")
    public VueData captcha() throws IOException {

        // 測試用資料
        // String key = "aaaaa"
        // String code = "11111"

        String key = UUID.randomUUID().toString();
        String code = producer.createText();

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        Base64.Encoder encoder = Base64.getEncoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encodeToString(outputStream.toByteArray());

        //儲存到Redis並設定過期時間
        redisUtil.set(key, code, 60 * 5);

        return VueData.ok(
                MapUtil.builder()
                        .put("token", key)
                        .put("base64Img", base64Img)
                        .build()
        );
    }
}
