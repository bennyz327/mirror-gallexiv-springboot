package com.team.gallexiv.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

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
    @Basic
    @Column(name = "commentStatus")
    private String commentStatus;

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

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Comment comment = (Comment) o;
//        return commentId == comment.commentId && Objects.equals(commentText, comment.commentText) && Objects.equals(commentTime, comment.commentTime) && Objects.equals(commentStatus, comment.commentStatus);
//    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, commentText, commentTime, commentStatus);
    }

    public Post getPostByPostId() {
        return postByPostId;
    }

    public void setPostByPostId(Post postByPostId) {
        this.postByPostId = postByPostId;
    }

    public Userinfo getUserinfoByUserId() {
        return userinfoByUserId;
    }

    public void setUserinfoByUserId(Userinfo userinfoByUserId) {
        this.userinfoByUserId = userinfoByUserId;
    }

    public Comment getCommentByParentCommentId() {
        return commentByParentCommentId;
    }

    public void setCommentByParentCommentId(Comment commentByParentCommentId) {
        this.commentByParentCommentId = commentByParentCommentId;
    }

    public Collection<Comment> getCommentsByCommentId() {
        return commentsByCommentId;
    }

    public void setCommentsByCommentId(Collection<Comment> commentsByCommentId) {
        this.commentsByCommentId = commentsByCommentId;
    }
}
