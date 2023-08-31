package com.team.gallexiv.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;

@Setter
@Getter
@Entity
@DynamicUpdate
@Table(name = "plan", schema = "gallexiv")
public class PlanForShow {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "planId")
    private int planId;

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
    @Column(name = "planPicture")
    private String planPicture;

    @Basic
    @Column(name = "ownerId")
    private int ownerId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "plan_status", referencedColumnName = "code_id")
//    @JsonIncludeProperties("statusName")
//    private Status planStatusByStatusId;
//
//    @JsonIncludeProperties({"postTitle","postPublic","post_status"})
//    @OneToMany(mappedBy = "planByPlanId")
//    private Collection<Post> postsByPlanId;
//
//    @JsonIncludeProperties("subscription_status")
//    @OneToMany(mappedBy = "planByPlanId")
//    private Collection<UserSubscription> userSubscriptionsByPlanId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ownerId", referencedColumnName = "userId")
//    @JsonIncludeProperties({"userName","user_status"})
//    private Userinfo ownerIdByUserId;

}
