package com.team.gallexiv.data.api.Ecpay;

import com.team.gallexiv.data.dto.EcpayDto;
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
}