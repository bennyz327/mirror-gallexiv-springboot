package com.team.gallexiv.data.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserSubscriptionDao extends JpaRepository<UserSubscription, Integer> {

    @Query("select u from UserSubscription u where u.subscriptionId = ?1 and u.planByPlanId.planId is not null ")
    Optional<UserSubscription> SubscriptionId(int subscriptionId);

    // 用 userId 找到 userSubscription
    @Query("select u from UserSubscription u where u.userinfoByUserId.userId = ?1 and u.planByPlanId.planId = ?2 ")
    Optional<UserSubscription> fUserSubscriptionByUserIdAndPlanId(int userId, int planId);
}
