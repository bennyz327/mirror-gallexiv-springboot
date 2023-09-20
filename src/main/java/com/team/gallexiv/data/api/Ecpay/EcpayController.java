package com.team.gallexiv.data.api.Ecpay;

import com.team.gallexiv.data.dto.EcpayDto;
import com.team.gallexiv.data.model.EcpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.team.gallexiv.data.model.EcpayService;

@RestController
public class EcpayController {

    @Autowired
    EcpayService ecpayService;

    @PostMapping("/ecpayCheckout")
    public String ecpayCheckout(@RequestBody EcpayDto request) {
        String aioCheckOutALLForm = ecpayService.ecpayCheckout(request);

        return aioCheckOutALLForm;
    }
}