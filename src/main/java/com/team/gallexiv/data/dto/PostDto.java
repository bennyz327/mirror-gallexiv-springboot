package com.team.gallexiv.data.dto;

import lombok.Data;

@Data
public class PostDto {
    Integer userId;
    String postTitle;
    String postContent;
    Integer postPublic;
    Integer postAgeLimit;
    String[] tagArr;
    Integer planId;

}