package com.team.gallexiv.data.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Collection;

@Setter
@Getter
@Entity
@Table(name = "comment")
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

    @PrePersist
    public void onCommentCreate() {
        if (commentTime == null) {
            commentTime = new Timestamp(System.currentTimeMillis());
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId", nullable = false)
    @JsonIncludeProperties({ "postId" })
    private Post postByPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    @JsonIncludeProperties({ "userId", "userName","avatar" })
    private Userinfo userinfoByUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCommentId", referencedColumnName = "commentId")
    @JsonIncludeProperties({ "userId", "parentCommentId", "commentText" })
    @JsonProperty("parentCommentId")
    private Comment commentByParentCommentId;

    @OneToMany(mappedBy = "commentByParentCommentId")
    private Collection<Comment> commentsByCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_status", referencedColumnName = "code_id")
    @JsonIncludeProperties({ "statusId", "statusType", "statusCategory", "statusName" })
    // @JsonIncludeProperties({ "statusId", "statusName" })
    private Status commentStatusByStatusId;

}
