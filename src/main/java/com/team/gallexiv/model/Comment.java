package com.team.gallexiv.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @Column(name = "commentTime")
    private Timestamp commentTime;
//    @Basic
//    @Column(name = "commentStatus")
//    private String commentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId", nullable = false)
    private Post postByPostId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private Userinfo userinfoByUserId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCommentId", referencedColumnName = "commentId")
    private Comment commentByParentCommentId;

    @OneToMany(mappedBy = "commentByParentCommentId")
    private Collection<Comment> commentsByCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_status", referencedColumnName = "code_id")
    private Status commentStatusByStatusId;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Comment comment = (Comment) o;
//        return commentId == comment.commentId && Objects.equals(commentText, comment.commentText) && Objects.equals(commentTime, comment.commentTime) && Objects.equals(commentStatus, comment.commentStatus);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(commentId, commentText, commentTime, commentStatus);
//    }

    public void setPostByPostId(Post postByPostId) {
        this.postByPostId = postByPostId;
    }

    public void setUserinfoByUserId(Userinfo userinfoByUserId) {
        this.userinfoByUserId = userinfoByUserId;
    }

    public void setCommentByParentCommentId(Comment commentByParentCommentId) {
        this.commentByParentCommentId = commentByParentCommentId;
    }

    public void setCommentsByCommentId(Collection<Comment> commentsByCommentId) {
        this.commentsByCommentId = commentsByCommentId;
    }


}
