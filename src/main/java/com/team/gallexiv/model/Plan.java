package com.team.gallexiv.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Collection;

@Getter
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

//    public void setPlanId(int planId) {
//        this.planId = planId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public void setPlanName(String planName) {
//        this.planName = planName;
//    }
//
//    public void setPlanPrice(int planPrice) {
//        this.planPrice = planPrice;
//    }
//
//    public void setPlanDescription(String planDescription) {
//        this.planDescription = planDescription;
//    }
//
//    public void setPlanStatus(String planStatus) {
//        this.planStatus = planStatus;
//    }
//
//    public void setPlanPicture(String planPicture) {
//        this.planPicture = planPicture;
//    }
//
//    public void setPostsByPlanId(Collection<Post> postsByPlanId) {
//        this.postsByPlanId = postsByPlanId;
//    }
//
//    public void setUserSubscriptionsByPlanId(Collection<UserSubscription> userSubscriptionsByPlanId) {
//        this.userSubscriptionsByPlanId = userSubscriptionsByPlanId;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Plan plan = (Plan) o;
//        return planId == plan.planId && userId == plan.userId && planPrice == plan.planPrice && Objects.equals(planName, plan.planName) && Objects.equals(planDescription, plan.planDescription) && Objects.equals(planStatus, plan.planStatus) && Objects.equals(planPicture, plan.planPicture);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(planId, userId, planName, planPrice, planDescription, planStatus, planPicture);
//    }

}
