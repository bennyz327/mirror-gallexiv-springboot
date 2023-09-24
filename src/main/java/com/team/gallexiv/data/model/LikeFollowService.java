package com.team.gallexiv.data.model;

import com.team.gallexiv.common.lang.VueData;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class LikeFollowService {

    private LikeFollowDao likeFollowD;

    private PostDao postD;

    public void LikeFollow(LikeFollowDao likeFollowD,PostDao postD){
        this.likeFollowD = likeFollowD;
        this.postD = postD;
    }


}
