package com.team.gallexiv.data.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCommentDto {
    private Integer commentId;
    private Integer postId;
    private Integer userId;
    private String commentText;
    private String userName;
    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp commentTime;

    private Integer parentCommentId;
    private Integer comment_status;

}
