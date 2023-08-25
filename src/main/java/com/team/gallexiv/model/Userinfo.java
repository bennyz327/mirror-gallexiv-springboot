package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "userinfo", schema = "gallexiv")
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

    @JsonIncludeProperties("commentId")
    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    private Collection<Comment> commentsByUserId;

    @JsonIncludeProperties("postTitle")
    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    private Collection<Post> postsListByUserId;

//    @JsonIgnore
//    @OneToMany(mappedBy = "userinfoBySessionUserId",fetch = FetchType.LAZY)
//    private Collection<Session> sessionsByUserId;

    @JsonIgnore
    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    private Collection<UserSubscription> userSubscriptionsByUserId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private AccountRole accountRoleByRoleId;

    @JsonIncludeProperties({"code_id","system_type","code_category","code_name"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status", referencedColumnName = "code_id")
    private Status userStatusByStatusId;

    @JsonIncludeProperties("planName")
    @OneToMany(mappedBy = "ownerIdByUserId")
    private Collection<Plan> planByPlanId;

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
}
