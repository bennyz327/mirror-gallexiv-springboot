package com.team.gallexiv.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "userSubscription", schema = "gallexiv")
public class UserSubscription {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "subscriptionId")
    private int subscriptionId;
    @Basic
    @Column(name = "userId")
    private int userId;
    @Basic
    @Column(name = "planId")
    private int planId;
    @Basic
    @Column(name = "subscriptionStartTime")
    private Timestamp subscriptionStartTime;
    @Basic
    @Column(name = "subscriptionStatus")
    private String subscriptionStatus;
    @ManyToOne
    @JoinColumn(referencedColumnName = "userId", nullable = false)
    private Userinfo userinfoByUserId;
    @ManyToOne
    @JoinColumn(referencedColumnName = "planId", nullable = false)
    private Plan planByPlanId;

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public Timestamp getSubscriptionStartTime() {
        return subscriptionStartTime;
    }

    public void setSubscriptionStartTime(Timestamp subscriptionStartTime) {
        this.subscriptionStartTime = subscriptionStartTime;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSubscription that = (UserSubscription) o;
        return subscriptionId == that.subscriptionId && userId == that.userId && planId == that.planId && Objects.equals(subscriptionStartTime, that.subscriptionStartTime) && Objects.equals(subscriptionStatus, that.subscriptionStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriptionId, userId, planId, subscriptionStartTime, subscriptionStatus);
    }

    public Userinfo getUserinfoByUserId() {
        return userinfoByUserId;
    }

    public void setUserinfoByUserId(Userinfo userinfoByUserId) {
        this.userinfoByUserId = userinfoByUserId;
    }

    public Plan getPlanByPlanId() {
        return planByPlanId;
    }

    public void setPlanByPlanId(Plan planByPlanId) {
        this.planByPlanId = planByPlanId;
    }
}
