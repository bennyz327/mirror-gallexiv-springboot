package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;

@Setter
@Getter
@Entity
@Table(name = "plan", schema = "gallexiv")
public class Plan {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_status", referencedColumnName = "code_id")
    @JsonIncludeProperties({"statusId","statusType","statusCategory","statusName"})
    private Status planStatusByStatusId;

    @OneToMany(mappedBy = "planByPlanId",cascade = CascadeType.ALL)
    @JsonIncludeProperties({"postId"})
    private Collection<Post> postsByPlanId;

    @OneToMany(mappedBy = "planByPlanId")
    @JsonIncludeProperties({"subscriptionId"})
    private Collection<UserSubscription> userSubscriptionsByPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId", referencedColumnName = "userId")
    @JsonIncludeProperties({"userId","userName","userEmail","accountRoleByRoleId"})
    private Userinfo ownerIdByUserId;

    @Override
    public String toString() {
        return "Plan{" +
                "planId=" + planId +
                ", planName='" + planName + '\'' +
                ", planPrice=" + planPrice +
                ", planDescription='" + planDescription + '\'' +
                ", planPicture='" + planPicture + '\'' +
                '}';
    }
}
