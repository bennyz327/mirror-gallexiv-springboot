package com.team.gallexiv.data.dto;

import com.team.gallexiv.data.model.Tag;
import lombok.Data;

@Data
public class PostDto {
    String title;
    String description;
    int postAgeLimit;
    int postPublic;
    Tag tag;
}
