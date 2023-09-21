package com.team.gallexiv.data.api.Ecpay;

import com.team.gallexiv.data.dto.EcpayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EcpayController {

    @Autowired
    EcpayService ecpayService;

    @PostMapping("/ecpayCheckout")
    public String ecpayCheckout(@RequestBody EcpayDto request) {
        return ecpayService.ecpayCheckout(request);
    }
}