package com.team.gallexiv.data.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SubCommentDto {
    private Integer commentId;
    private Integer postId;
    private Integer userId;
    private String commentText;
    private String userName;

    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp commentTime;

    private Integer parentCommentId;
    private Integer comment_status;


    public Integer getCommentId() {
        return commentId;
    }
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
    public Integer getPostId() {
        return postId;
    }
    public void setPostId(Integer postId) {
        this.postId = postId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
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
    public Integer getParentCommentId() {
        return parentCommentId;
    }
    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
    public Integer getComment_status() {
        return comment_status;
    }
    public void setComment_status(Integer comment_status) {
        this.comment_status = comment_status;
    }


    

}
