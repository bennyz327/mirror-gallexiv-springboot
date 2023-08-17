package com.team.gallexiv.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "plan", schema = "gallexiv")
public class Plan {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "planId")
    private int planId;
    @Basic
    @Column(name = "userId")
    private int userId;
    @Basic
    @Column(name = "planName")
    private String planName;
    @Basic
    @Column(name = "planPrice")
    private int planPrice;
    @Basic
    @Column(name = "planDescription")
    private String planDescription;
    @Basic
    @Column(name = "planStatus")
    private String planStatus;
    @Basic
    @Column(name = "planPicture")
    private String planPicture;
    @OneToMany(mappedBy = "planByPlanId")
    private Collection<Post> postsByPlanId;
    @OneToMany(mappedBy = "planByPlanId")
    private Collection<UserSubscription> userSubscriptionsByPlanId;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(int planPrice) {
        this.planPrice = planPrice;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanPicture() {
        return planPicture;
    }

    public void setPlanPicture(String planPicture) {
        this.planPicture = planPicture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return planId == plan.planId && userId == plan.userId && planPrice == plan.planPrice && Objects.equals(planName, plan.planName) && Objects.equals(planDescription, plan.planDescription) && Objects.equals(planStatus, plan.planStatus) && Objects.equals(planPicture, plan.planPicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, userId, planName, planPrice, planDescription, planStatus, planPicture);
    }

    public Collection<Post> getPostsByPlanId() {
        return postsByPlanId;
    }

    public void setPostsByPlanId(Collection<Post> postsByPlanId) {
        this.postsByPlanId = postsByPlanId;
    }

    public Collection<UserSubscription> getUserSubscriptionsByPlanId() {
        return userSubscriptionsByPlanId;
    }

    public void setUserSubscriptionsByPlanId(Collection<UserSubscription> userSubscriptionsByPlanId) {
        this.userSubscriptionsByPlanId = userSubscriptionsByPlanId;
    }
}
