package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(name = "planPicture")
    private String planPicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_status", referencedColumnName = "code_id")
    private Status planStatusByStatusId;

    @JsonIgnore
    @OneToMany(mappedBy = "planByPlanId")
    private Collection<Post> postsByPlanId;

    @JsonIgnore
    @OneToMany(mappedBy = "planByPlanId")
    private Collection<UserSubscription> userSubscriptionsByPlanId;


}
