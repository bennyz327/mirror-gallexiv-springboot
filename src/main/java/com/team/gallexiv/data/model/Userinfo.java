package com.team.gallexiv.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "userinfo")
public class Userinfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userId")
    private int userId;

    @Basic
    @Column(name = "userName")
    private String userName;

    @Basic
    @Column(name = "account")
    private String account;

    @Basic
    @Column(name = "pWord")
    private String pWord;

    @Basic
    @Column(name = "userEmail")
    private String userEmail;

    @Basic
    @Column(name = "email_verified")
    private Integer email_verified;

    @Basic
    @Column(name = "birthday")
    private Timestamp birthday;

    @Basic
    @Column(name = "gender")
    private String gender;

    @Basic
    @Column(name = "avatar")
    private String avatar;

    @Basic
    @Column(name = "intro")
    private String intro;

    @Basic
    @Column(name = "createTime")
    private Timestamp createTime;

    @Basic
    @Column(name = "first_name")
    private String first_name;

    @Basic
    @Column(name = "last_name")
    private String last_name;

    @Basic
    @Column(name = "modified_by")
    private Integer modified_by;

    @Basic
    @Column(name = "last_modified")
    private Timestamp last_modified;

    @Basic
    @Column(name = "background_image")
    private String background_image;

    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    @JsonIncludeProperties({"commentId","commentText","commentStatusByStatusId"})
    private Collection<Comment> commentsByUserId;

    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    @JsonIncludeProperties({"postId","postTitle","postStatusByStatusId"})
    private Collection<Post> postsListByUserId;

    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    @JsonIncludeProperties({"subscriptionId","subscriptionStatusByStatusId"})
    private Collection<UserSubscription> userSubscriptionsByUserId;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    @JsonIncludeProperties({"roleId","roleName","roleStatusByStatusId"})
    private AccountRole accountRoleByRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status", referencedColumnName = "code_id")
    @JsonIncludeProperties({"statusId","statusType","statusCategory","statusName"})
    private Status userStatusByStatusId;

    @OneToMany(mappedBy = "ownerIdByUserId")
    @JsonIncludeProperties({"planId","planName","planStatusByStatusId"})
    private Collection<Plan> planByPlanId;

    @OneToMany(mappedBy = "userByUserId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIncludeProperties({"likeId"})
    private Collection<LikeFollow> likeFollowByPostId;

    @OneToMany(mappedBy = "userinfoByUserId")
    @JsonIncludeProperties({"linkSite","linkSource"})
    private Collection<LinkMapping> linkMappingsByUserId;

    public Userinfo() {
    }

    public Userinfo(String userName, String userEmail, String account, String pWord, AccountRole role, Status status){
        this.userName = userName;
        this.userEmail = userEmail;
        this.account = account;
        this.pWord = pWord;
        this.accountRoleByRoleId = role;
        this.userStatusByStatusId = status;
    }

    @Override
    public String toString() {
        return "Userinfo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", pWord='" + pWord + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", email_verified=" + email_verified +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", avatar='" + avatar + '\'' +
                ", intro='" + intro + '\'' +
                ", createTime=" + createTime +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", modified_by=" + modified_by +
                ", last_modified=" + last_modified +
                '}';
    }

    static public Userinfo createUserByAccouut(String account) {
        Userinfo new_user = new Userinfo();
        new_user.setAccount(account);
        return new_user;
    }

}
