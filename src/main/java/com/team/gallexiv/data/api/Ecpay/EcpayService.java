package com.team.gallexiv.data.api.Ecpay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

import com.team.gallexiv.data.dto.EcpayDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Slf4j
@Service
public class EcpayService {

    public String ecpayCheckout(EcpayDto request) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
        log.info("產生訂單時間：{}",formattedDate);

        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        AllInOne all = new AllInOne("");

        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(uuId);
        obj.setMerchantTradeDate(formattedDate);
        System.out.println(formattedDate);
        obj.setTotalAmount(request.getTotalAmount());
        obj.setTradeDesc(request.getTradeDesc());
        obj.setItemName(request.getItemName());
        obj.setReturnURL(request.getReturnURL());
        obj.setClientBackURL(request.getClientBackURL());
        obj.setNeedExtraPaidInfo("N");
        String form = all.aioCheckOut(obj, null);

        return form;
    }
}