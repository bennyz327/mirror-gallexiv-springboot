package com.team.gallexiv.ctrl.auth;

import com.google.code.kaptcha.Producer;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.team.gallexiv.lang.VueData;
import com.team.gallexiv.model.CaptchaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@RestController
public class AuthController extends BaseController {

    @Autowired
    Producer producer;


    @GetMapping("/captcha")
    public VueData captcha() throws IOException {

        //產生驗證碼和圖片
        String key = UUID.randomUUID().toString();
        String code = producer.createText();
        BufferedImage image = producer.createImage(code);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);

            //轉成base64
            Base64.Encoder encoder = Base64.getEncoder();
            String str = "data:image/jpeg;base64,";
            String base64Img = str + encoder.encodeToString(baos.toByteArray());

            //儲存到MySQL
            CaptchaInfo captchaInfo = new CaptchaInfo(key, code);

            return VueData.ok(
                    MapUtil
                            .builder()
                            .put("token", key)
                            .put("base64Img", base64Img)
                            .build()
            );

            return "captcha";
        }
    }
}
