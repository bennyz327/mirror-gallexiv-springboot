package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
@DynamicInsert
@Table(name = "userSubscription", schema = "gallexiv")
public class UserSubscription {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "subscriptionId")
    private int subscriptionId;

    @Basic
    @Column(name = "subscriptionStartTime")
    private Timestamp subscriptionStartTime;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private Userinfo userinfoByUserId;

    @ManyToOne
    @JoinColumn(name = "planId", referencedColumnName = "planId", nullable = false)
    private Plan planByPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_status", referencedColumnName = "code_Id")
    @JsonIncludeProperties({"statusId","statusCategory","statusName"})
    private Status subscriptionStatusByStatusId;



//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserSubscription that = (UserSubscription) o;
//        return subscriptionId == that.subscriptionId && userId == that.userId && planId == that.planId && Objects.equals(subscriptionStartTime, that.subscriptionStartTime) && Objects.equals(subscriptionStatus, that.subscriptionStatus);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(subscriptionId, userId, planId, subscriptionStartTime, subscriptionStatus);
//    }

}
