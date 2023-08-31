package com.team.gallexiv.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@Entity
@DynamicInsert
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

    // M2O取得Userinfo物件
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonIncludeProperties({ "userId", "userName", "userStatusByStatusId" })
    private Userinfo userinfoByUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planId", referencedColumnName = "planId")
    @JsonIncludeProperties({ "planId", "planName", "planStatusByStatusId" })
    private Plan planByPlanId;

    // M2O取得Comment物件

    @OneToMany(mappedBy = "postByPostId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIncludeProperties({ "commentId", "userinfoByUserId", "commentText", "commentStatusByStatusId" })
    private Collection<Comment> commentsByPostId;

    // mappedBy = "postByPostId" 要指定的是Picture裡面的屬性名稱，不是資料庫的欄位名稱

    @OneToMany(mappedBy = "postByPostId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIncludeProperties({ "pictureId", "imgPath", "picture_status" })
    private Collection<Picture> picturesByPostId;

    @OneToMany(mappedBy = "postByPostId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIncludeProperties({ "likeId" })
    private Collection<LikeFollow> likeFollowByPostId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "tagPost",
            joinColumns = {@JoinColumn(name = "postId")},
            inverseJoinColumns = {@JoinColumn(name = "tagId")}
    )
    @JsonIncludeProperties({"tagId","tagName"})
    private Collection<Tag> tagsByPostId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_status", referencedColumnName = "code_id")
    @JsonIncludeProperties({"statusId","statusType","statusCategory","statusName"})
    private Status postStatusByStatusId;

}
