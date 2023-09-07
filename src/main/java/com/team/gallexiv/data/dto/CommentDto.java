package com.team.gallexiv.data.dto;

import io.micrometer.common.util.internal.logging.InternalLogger;
import lombok.Data;

@Data
public class CommentDto {
    Integer postId;
    String commentText;
    Integer parentCommentId;

}
