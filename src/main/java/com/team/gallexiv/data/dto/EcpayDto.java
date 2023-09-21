package com.team.gallexiv.data.dto;

import lombok.Data;

@Data
public class EcpayDto {
    String tradeDate;
    String totalAmount;
    String tradeDesc;
    String itemName;
    String returnURL;
    String clientBackURL;


}
