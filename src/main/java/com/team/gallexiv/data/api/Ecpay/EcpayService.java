package com.team.gallexiv.data.api.Ecpay;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

import com.team.gallexiv.data.dto.EcpayDto;
import com.team.gallexiv.data.dto.EcpayQueryDto;
import com.team.gallexiv.data.model.*;
import ecpay.payment.integration.domain.QueryTradeInfoObj;
import ecpay.payment.integration.domain.QueryTradeObj;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Slf4j
@Service
public class EcpayService {

    @Autowired
    SubscriptionInvoiceService subscriptionInvoiceS;
    @Autowired
    UserSubscriptionDao userSubscriptionD;
    @Autowired
    PlanDao planD;
    @Autowired
    UserService userS;

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
        obj.setNeedExtraPaidInfo("N");

        String form = all.aioCheckOut(obj, null);

        //新增訂閱關係
        log.info("即將新增訂閱紀錄，ID：{}",request.getPlanId());
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setPlanByPlanId(planD.findById(Integer.valueOf(request.getPlanId())).get());
        String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userSubscription.setUserinfoByUserId(userS.getUserByAccount(accoutName));
        userSubscription.setSubscriptionStatusByStatusId(new Status(16));
        userSubscriptionD.save(userSubscription);

        //新增訂單
        log.info("即將為訂閱紀錄新增新訂單，ID：{}",uuId);
        SubscriptionInvoice payInvoice = new SubscriptionInvoice();
        payInvoice.setUserSubscriptionBySubscriptionId(userSubscription);
        payInvoice.setTotalAmount(BigDecimal.valueOf(Integer.parseInt(request.getTotalAmount())));
        payInvoice.setTradeNo(uuId);
        payInvoice.setTradeDate(new Timestamp(date.getTime()));
        payInvoice.setTradeStatusCode(19);
        payInvoice.setSettleMonth(formattedDate.substring(0, 7));
        subscriptionInvoiceS.insertSubscriptionInvoice(payInvoice);

        return form;
    }

//    public String queryTradeInfo(EcpayQueryDto request) {
//    	AllInOne all = new AllInOne("");
//        QueryTradeInfoObj qObj = new QueryTradeInfoObj();
//        qObj.setMerchantTradeNo(request.getTradeNo());
//        return all.queryTradeInfo(qObj);
//    }

    //查詢每個訂單在綠界的狀態，並更新資料庫
    public List<Plan> queryAndRenewTradeInfo(List<Plan> planList){

        return null;
    }
}