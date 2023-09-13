package com.team.gallexiv.data.dto;

import lombok.Data;

@Data
public class CommentDto {
    Integer postId;
    String commentText;
    Integer parentCommentId;

}
