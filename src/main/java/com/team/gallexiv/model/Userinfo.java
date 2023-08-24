package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@Entity
@JsonPropertyOrder({"userEmail","userName"})
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

    @JsonIgnore
    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    private Collection<Comment> commentsByUserId;

    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    private Collection<Post> postsListByUserId;

//    @JsonIgnore
//    @OneToMany(mappedBy = "userinfoBySessionUserId",fetch = FetchType.LAZY)
//    private Collection<Session> sessionsByUserId;

    @JsonIgnore
    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    private Collection<UserSubscription> userSubscriptionsByUserId;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private AccountRole accountRoleByRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status", referencedColumnName = "code_id")
    private Status userStatusByStatusId;

    @OneToMany(mappedBy = "ownerIdByUserId")
    private Collection<Plan> planByPlanId;

}
