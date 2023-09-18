package com.team.gallexiv.data.model;

import com.team.gallexiv.common.lang.VueData;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UserSubscriptionService {

    private UserSubscriptionDao userSubscriptionD;

    private UserDao userD;

    public UserSubscriptionService(UserSubscriptionDao userSubscriptionD,UserDao userD) {
        this.userSubscriptionD = userSubscriptionD;
        this.userD = userD;
    }

    public VueData findPersonalSubscription(){
        String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Userinfo> optionalUserinfo = userD.findByAccount(accoutName);
        if (optionalUserinfo.isPresent()){
            int userId = optionalUserinfo.get().getUserId();
            return VueData.ok(userSubscriptionD.findById(userId));
        }
        return VueData.error("沒有userId");

    }


}
