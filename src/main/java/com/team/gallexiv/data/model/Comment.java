package com.team.gallexiv.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Collection;

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

    // private Integer parentCommentId;

    // 寫入DB前先建立時間 // TODO 為甚麼要加時間?
    @PrePersist
    public void onCommentCreate() {
        if (commentTime == null) {
            commentTime = new Timestamp(System.currentTimeMillis());
        }
    }

    // public void setParentCommentId(Integer parentCommentId) {
    // this.parentCommentId = parentCommentId;
    // }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId", nullable = false)
    @JsonIncludeProperties({ "postId" })
    private Post postByPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    @JsonIncludeProperties({ "userId", "userName" })
    private Userinfo userinfoByUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCommentId", referencedColumnName = "commentId")
    @JsonIncludeProperties({ "userId", "parentCommentId", "commentText" })
    private Comment commentByParentCommentId;

    // @Transient // 标记为 transient，不会持久化到数据库
    // @JsonInclude(JsonInclude.Include.NON_NULL) // 只包含非空的字段
    // private Integer parentCommentId; // 新增一个字段用于存储父评论的 commentId

    // // 在构造 JSON 数据前设置 parentCommentId 的值
    // @PostLoad
    // public void setParentCommentId() {
    // if (commentByParentCommentId != null) {
    // parentCommentId = commentByParentCommentId.getCommentId();
    // }
    // }

    @OneToMany(mappedBy = "commentByParentCommentId")
    @JsonIncludeProperties({ "userId", "commentText", "parentCommentId" })
    private Collection<Comment> commentsByCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_status", referencedColumnName = "code_id")
    @JsonIncludeProperties({ "statusId", "statusType", "statusCategory", "statusName" })
    // @JsonIncludeProperties({ "statusId", "statusName" })
    private Status commentStatusByStatusId;

}
