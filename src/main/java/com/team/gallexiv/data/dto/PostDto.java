package com.team.gallexiv.data.dto;

import com.team.gallexiv.data.model.Plan;
import com.team.gallexiv.data.model.Tag;
import com.team.gallexiv.data.model.Userinfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class PostDto {
    int userId;
    String postTitle;
    String postContent;
    Integer postPublic;
    Integer postAgeLimit;
    String[] tagArr;
}
