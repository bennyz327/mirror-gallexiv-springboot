package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "code_mapping",schema = "gallexiv")
public class Status {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private int statusId;

    @Column(name = "system_type")
    private String statusType;

    @Column(name = "code_category")
    private String statusCategory;

    @Column(name = "code_name")
    private String statusName;

    @Column(name = "code_description")
    private String statusDescription;

    @OneToMany(mappedBy = "roleStatusByStatusId")
    private Collection<AccountRole> statusByRoleId;

    @OneToMany(mappedBy = "commentStatusByStatusId")
    private Collection<Comment> statusByCommentId;

    @OneToMany(mappedBy = "pictureStatusByStatusId")
    private Collection<Picture> statusByPictureId;


    @OneToMany(mappedBy = "planStatusByStatusId")
    private Collection<Plan> statusByPlanId;

    @OneToMany(mappedBy = "postStatusByStatusId")
    private Collection<Post> statusByPostId;

    @OneToMany(mappedBy = "userStatusByStatusId")
    private Collection<Userinfo> statusByUserId;

    @OneToMany(mappedBy = "subscriptionStatusByStatusId")
    private Collection<UserSubscription> statusBySubscriptionId;

    public Status(){

    }
    public Status(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return String.valueOf(statusId)+","+statusName+","+statusCategory+";";
    }
}
