package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@Entity
@Table(name = "comment", schema = "gallexiv")
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "commentId")
    private int commentId;

    @Basic
    @Column(name = "commentText")
    private String commentText;

    @Basic
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "commentTime")
    private Timestamp commentTime;
    // private Instant commentTime; // for lleon's own memo

    // 寫入DB前先建立時間
    @PrePersist
    public void onCommentCreate() {
        if (commentTime == null) {
            commentTime = new Timestamp(System.currentTimeMillis());
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId", nullable = false)
    @JsonIncludeProperties({"postId"})
    private Post postByPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    @JsonIncludeProperties({"userId","userName"})
    private Userinfo userinfoByUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCommentId", referencedColumnName = "commentId")
    @JsonIncludeProperties({ "userId", "parentCommentId", "commentText" })
    private Comment commentByParentCommentId;

    @OneToMany(mappedBy = "commentByParentCommentId")
    private Collection<Comment> commentsByCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_status", referencedColumnName = "code_id")
    @JsonIncludeProperties({"statusId","statusType","statusCategory","statusName"})
//    @JsonIncludeProperties({ "statusId", "statusName" })
    private Status commentStatusByStatusId;

}
