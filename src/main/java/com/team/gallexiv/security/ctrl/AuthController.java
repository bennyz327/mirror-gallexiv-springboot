package com.team.gallexiv.security.ctrl;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.team.gallexiv.common.lang.Const;
import com.team.gallexiv.common.lang.VueData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
public class AuthController extends BaseController {

    @Autowired
    Producer producer;

    @GetMapping("/captcha")
    public VueData captcha() throws IOException {

//         測試用資料
        //TODO 開發階段限定
         String key = "aaaaa";
         String code = "11111";

//        String key = UUID.randomUUID().toString();
//        String code = producer.createText();

        BufferedImage image = producer.createImage(code);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);

        Base64.Encoder encoder = Base64.getEncoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encodeToString(baos.toByteArray());

        //TODO 開發階段限定
        //儲存到Redis並設定過期時間
        System.out.println("REDIS發送結果: "+redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120));
        System.out.println("本次的驗證碼key/code: "+key+"/"+redisUtil.hget(Const.CAPTCHA_KEY, key));

        return VueData.ok(
                MapUtil.builder()
                        .put("token", key)
                        .put("base64Img", base64Img)
                        .build()
        );
    }
}
