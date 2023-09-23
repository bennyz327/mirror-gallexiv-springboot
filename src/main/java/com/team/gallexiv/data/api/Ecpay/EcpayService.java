package com.team.gallexiv.data.api.Ecpay;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.team.gallexiv.data.dto.EcpayDto;
import com.team.gallexiv.data.model.*;
import ecpay.payment.integration.domain.QueryTradeInfoObj;
import lombok.extern.slf4j.Slf4j;
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
    SubscriptionInvoiceDao subscriptionInvoiceD;
    @Autowired
    UserSubscriptionDao userSubscriptionD;
    @Autowired
    UserSubscriptionService userSubscriptionS;
    @Autowired
    PlanDao planD;
    @Autowired
    UserService userS;

    public String ecpayCheckout(EcpayDto request) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
        log.info("產生訂單時間：{}", formattedDate);

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
        log.info("即將新增訂閱紀錄，ID：{}", request.getPlanId());
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setPlanByPlanId(planD.findById(Integer.valueOf(request.getPlanId())).get());
        String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userSubscription.setUserinfoByUserId(userS.getUserByAccount(accoutName));
        userSubscription.setSubscriptionStatusByStatusId(new Status(16));
        userSubscriptionD.save(userSubscription);

        //新增訂單
        log.info("即將為訂閱紀錄新增新訂單，ID：{}", uuId);
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
    public List<Plan> queryAndRenewTradeInfo(List<Plan> planList,Integer userId) {

        for (Plan plan : planList) {
            //訂閱紀錄中有紀錄的皆查詢，而且查詢的是第一筆訂閱紀錄的第一筆訂單
            //TODO 應該要加入狀態條件判斷才對
            Optional<UserSubscription> subscriptionBetweenUserAndPlan = userSubscriptionD.fUserSubscriptionByUserIdAndPlanId(userId,plan.getPlanId());
            if(subscriptionBetweenUserAndPlan.isPresent()){
                if(subscriptionBetweenUserAndPlan.get().getSubscriptionInvoicesBySubscriptionId().iterator().hasNext()){
                    log.info("本方案關聯已產生訂單，將訂單編號帶入查詢");
                    //發出查詢請求
                    String result = queryByTradeNo(subscriptionBetweenUserAndPlan.get().getSubscriptionInvoicesBySubscriptionId().iterator().next().getTradeNo());
                    //把結果用&分開
                    String[] resultArray = result.split("&");
                    //找出陣列中開頭為TradeStatus的元素
                    for (String s : resultArray) {
                        if (s.startsWith("TradeStatus")) {
                            log.info("查詢到的綠界訂單狀態：{}", s);
                            String status = s.split("=")[1];
                            if (status.equals("1")) {
                                log.info("訂閱 ID 為 {} 的方案,已成功付款", plan.getPlanId());
                                log.info("即將更新資料庫訂閱關係的狀態");
                                UserSubscription newSub = subscriptionBetweenUserAndPlan.get();
                                newSub.setSubscriptionStatusByStatusId(new Status(15));
                                userSubscriptionD.save(subscriptionBetweenUserAndPlan.get());
                                plan.isSubscribedPayed = true;
                                plan.expireDate = new Timestamp(newSub.getSubscriptionStartTime().getTime() + 2592000000L).toString().substring(0, 10);
                            } else {
                                log.info("訂閱 ID 為 {} 的方案,尚未付款", plan.getPlanId());
                            }
                        }
                    }
                }else {
                    log.info("本方案尚未產生訂單");
                    planList.remove(plan);
                }
            }else{
                log.info("本方案和使用者無訂閱關係");
                planList.remove(plan);
            }
        }

        //方案清單物件+方案資料庫的狀態更新完成
        return planList;
    }

    public String queryByTradeNo(String tradeNo) {
        log.info("即將發出網路請求查詢訂單編號：{}", tradeNo);
    	AllInOne all = new AllInOne("");
        QueryTradeInfoObj qObj = new QueryTradeInfoObj();
        qObj.setMerchantTradeNo(tradeNo);
        return all.queryTradeInfo(qObj);
    }
}