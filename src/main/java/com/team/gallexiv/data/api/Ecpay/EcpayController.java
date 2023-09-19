package com.team.gallexiv.data.api.Ecpay;

import com.team.gallexiv.data.model.EcpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.gallexiv.data.model.EcpayService;

@RestController
public class EcpayController {

    @Autowired
    EcpayService ecpayService;

    @PostMapping("/ecpayCheckout")
    public String ecpayCheckout() {
        String aioCheckOutALLForm = ecpayService.ecpayCheckout();

        return aioCheckOutALLForm;
    }
}