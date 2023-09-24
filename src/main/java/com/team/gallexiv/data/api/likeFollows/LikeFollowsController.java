package com.team.gallexiv.data.api.likeFollows;

import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.model.LikeFollowService;
import com.team.gallexiv.data.model.Post;
import com.team.gallexiv.data.model.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LikeFollowsController {

    private LikeFollowService likeFollowS;

    private PostService postS;

    public void LikeFollowsController(LikeFollowService likeFollowS,PostService postS){
        this.likeFollowS = likeFollowS;
        this.postS = postS;
    }

//    @GetMapping("/likes")
//    public VueData likeNumByPostId(@RequestParam int postId){
//        return likeFollowS.likeCountByPostId(postId);
//    }
}
