package com.team.gallexiv.data.api.Subscriptions;

import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.model.UserSubscriptionService;
import org.springframework.web.bind.annotation.GetMapping;

public class UserSubscriptionsController {

    private UserSubscriptionService userSubscriptionS;

    public UserSubscriptionsController(UserSubscriptionService userSubscriptionS) {
        this.userSubscriptionS = userSubscriptionS;
    }

    @GetMapping("/subscription")
    public VueData findPersonalSub(){
        return userSubscriptionS.findPersonalSubscription();
    }

}
