package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@Entity
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
    @Column(name = "birthday")
    private Timestamp birthday;
    @Basic
    @Column(name = "gender")
    private String gender;
    @Basic
    @Column(name = "avatarPath")
    private String avatarPath;
    @Basic
    @Column(name = "intro")
    private String intro;
    @Basic
    @Column(name = "createTime")
    private Timestamp createTime;
    @Basic
    @Column(name = "userStatus")
    private String userStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    private Collection<Comment> commentsByUserId;

    @JsonManagedReference
    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    private Collection<Post> postsListByUserId;

    @JsonIgnore
    @OneToMany(mappedBy = "userinfoBySessionUserId",fetch = FetchType.LAZY)
    private Collection<Session> sessionsByUserId;

    @JsonIgnore
    @OneToMany(mappedBy = "userinfoByUserId",fetch = FetchType.LAZY)
    private Collection<UserSubscription> userSubscriptionsByUserId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private AccountRole accountRoleByRoleId;

}
