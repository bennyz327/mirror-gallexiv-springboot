package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "post", schema = "gallexiv")
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "postId")
    private int postId;
    @Basic
    @Column(name = "postTitle")
    private String postTitle;
    @Basic
    @Column(name = "postContent")
    private String postContent;
    @Basic
    @Column(name = "postTime")
    private Timestamp postTime;
    @Basic
    @Column(name = "postPublic")
    private Integer postPublic;
    @Basic
    @Column(name = "postAgeLimit")
    private Integer postAgeLimit;
    @Basic
    @Column(name = "postStatus")
    private String postStatus;

    //M2O取得Userinfo物件
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Userinfo userinfoByUserId;
    //單純取得userId
//    @Basic
//    @Column(name = "userId", insertable = false, updatable = false)
//    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planId", referencedColumnName = "planId")
    private Plan planByPlanId;

    //M2O取得Comment物件
    @JsonIgnore
    @OneToMany(mappedBy = "postByPostId", fetch = FetchType.LAZY)
    private Collection<Comment> commentsByPostId;

    //mappedBy = "postByPostId" 要指定的是Picture裡面的屬性名稱，不是資料庫的欄位名稱
    @JsonManagedReference
    @OneToMany(mappedBy = "postByPostId", fetch = FetchType.LAZY)
    private Collection<Picture> picturesByPostId;
//    @OneToMany(mappedBy = "postByPostId", orphanRemoval = true)
//    private Collection<Picture> pictures;


    //好像不需要讓這張表單被存取
//    @OneToMany(mappedBy = "postByPostId", fetch = FetchType.LAZY)
//    private Collection<TagPost> tagPostsByPostId;

    @ManyToMany(fetch = FetchType.LAZY/*, cascade = CascadeType.ALL*/)
    @JoinTable(name = "tagPost", joinColumns = {@JoinColumn(name = "postId")}, inverseJoinColumns = {@JoinColumn(name = "tagId")})
    private Collection<Tag> tagsByPostId;

}
