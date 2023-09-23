package com.team.gallexiv.data.api.Ecpay;

import com.team.gallexiv.data.dto.EcpayDto;
import com.team.gallexiv.data.dto.EcpayQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class EcpayController {

    @Autowired
    EcpayService ecpayService;

    @PostMapping(path = "/ecpayCheckout", consumes = "application/json")
    public String ecpayCheckout(@RequestBody EcpayDto request) {
        return ecpayService.ecpayCheckout(request);
    }

    //接收格式為FORMDATA，未部屬無法測試
    @PostMapping(path = "/pay/ecpayReturn", consumes = "application/x-www-form-urlencoded")
    public void ecpayReturn(@RequestBody String request) {
        System.out.println(request);
    }

}