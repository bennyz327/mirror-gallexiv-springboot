package com.team.gallexiv.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EcpayDto {
    private String tradeDate;
    private String totalAmount;
    private String tradeDesc;
    private String itemName;
    private String returnURL;
    private String clientBackURL;
}
