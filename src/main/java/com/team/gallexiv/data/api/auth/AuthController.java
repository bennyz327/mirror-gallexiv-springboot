package com.team.gallexiv.data.api.auth;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.team.gallexiv.common.lang.Const;
import com.team.gallexiv.common.lang.VueData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@RestController
public class AuthController extends BaseController {

    @Autowired
    Producer producer;

    @GetMapping("/captcha")
    public VueData captcha() throws IOException {

        // TODO 生產環境清理
//         String key = "aaaaa";
//         String code = "11111";
        //TODO 生產環境要用 要檢查用戶端，若重複要刪除舊的驗證碼紀錄
        String key = UUID.randomUUID().toString();
        String code = producer.createText();

        BufferedImage image = producer.createImage(code);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);

        Base64.Encoder encoder = Base64.getEncoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encodeToString(baos.toByteArray());


        boolean redisSendResult = redisUtil.hset(Const.CAPTCHA_REDIS_KEY, key, code, 120);
        log.info("REDIS發送結果: {}", redisSendResult);

        //TODO 生產環境清理
        log.info("本次的驗證碼key/code: {}/{}", key, redisUtil.hget(Const.CAPTCHA_REDIS_KEY, key));

        return VueData.ok(
                MapUtil.builder()
                        .put("token", key)
                        .put("base64Img", base64Img)
                        .build()
        );
    }
}
