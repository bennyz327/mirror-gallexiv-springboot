package com.team.gallexiv.data.model;

import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.dto.PostDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    final PostDao postD;
    final UserDao userD;
    final PlanDao planD;
    final TagDao tagD;
    final PictureDao pictureD;

    public PostService(PostDao postD, UserDao userD, TagDao tagD, PictureDao pictureD, PlanDao planD) {
        this.postD = postD;
        this.userD = userD;
        this.tagD = tagD;
        this.pictureD = pictureD;
        this.planD = planD;
    }

    // 取得單筆貼文
    public VueData getPostById(int postId) {
        Optional<Post> optionalPost = postD.findById(postId);
        if (optionalPost.isPresent()) {
            return VueData.ok(optionalPost.orElse(null));
        }
        return VueData.error("查詢失敗");
    }

    // 取得全部貼文
    public VueData getAllPost() {
        List<Post> result = postD.findAll();
        if (result.isEmpty()) {
            return VueData.error("查詢失敗");
        }
        return VueData.ok(result);
    }

    // 新增貼文
    public VueData insertPost(Post post) {

        Optional<Userinfo> optionalUserinfo = userD.findById(post.getUserinfoByUserId().getUserId());
        if (optionalUserinfo.isPresent()) {

            if (!(post.getPlanByPlanId() == null)) {
                Optional<Plan> optionalPlan = planD.findById(post.getPlanByPlanId().getPlanId());
                if (optionalPlan.isPresent()) {
                    post.setPlanByPlanId(optionalPlan.get());
                }
            }

            Collection<Tag> inputTags = post.getTagsByPostId();
            Collection<Tag> newTags = new ArrayList<>();
            System.out.println("test");
            System.out.println(inputTags);
            for (Tag inputTag : inputTags) {
                Optional<Tag> TagFindRsOptional = tagD.findByTagName(inputTag.getTagName());

                if (TagFindRsOptional.isPresent()) {
                    newTags.add(TagFindRsOptional.get());
                } else {
                    newTags.add(inputTag);
                }
            }
            post.setTagsByPostId(newTags);



            return VueData.ok(postD.save(post));
        }
        return VueData.error("新增失敗");
    }

    // 刪除貼文
    public VueData deletePostById(Integer postId) {
        Optional<Post> postOptional = postD.findById(postId);
        if (postOptional.isPresent()) {
            postD.deleteById(postId);
            return VueData.ok("刪除成功");
        }
        return VueData.error("刪除失敗");
    }


    // 更新貼文
    public VueData updatePostById(Post post) {

        Optional<Post> optional = postD.findById(post.getPostId());

        if (optional.isPresent()) {
            Post result = optional.get();
            result.setPostTitle(post.getPostTitle());
            result.setPostContent(post.getPostContent());
            result.setPostPublic(post.getPostPublic());
            result.setPostAgeLimit(post.getPostAgeLimit());

            Collection<Tag> inputTags = post.getTagsByPostId();

            Collection<Tag> existingTags = new ArrayList<>();

            for (Tag inputTag : inputTags) {
                Optional<Tag> existingTagOptional = tagD.findByTagName(inputTag.getTagName());

                if (existingTagOptional.isPresent()) {
                    existingTags.add(existingTagOptional.get());
                } else {
                    existingTags.add(inputTag);
                }
            }
            result.setTagsByPostId(existingTags);
            return VueData.ok(result);
        }
        return VueData.error("更新失敗");

    }

    public Userinfo getPostOwner(int id) {
        System.out.println("--查詢貼文作者--");

        System.out.println("貼文查詢開始");
        Optional<Post> currentPost = postD.findById(id);
        System.out.println("貼文查詢結束");

        if (currentPost.isPresent()) {

            System.out.println("開始進行貼文作者查詢");
            Optional<Userinfo> owner = userD.findByUserId(currentPost.get().getUserinfoByUserId().getUserId());
            System.out.println("貼文作者查詢結束");

            if (owner.isPresent()) {
                Userinfo ownerUser = owner.get();
                System.out.println("這是查到的使用者ID=" + ownerUser.getUserId());
                return ownerUser;
            }
            System.out.println("查無貼文作者");
            return null;
        }
        System.out.println("查無貼文資料");
        return null;
    }

    public VueData getPostByUserId(int userId){

        Optional<Userinfo> userinfo = userD.findByUserId(userId);
        if(userinfo.isPresent()){
            return VueData.ok(postD.findByUserinfoByUserId(userinfo.get()));
        }
        return VueData.error("查無該使用者貼文");
    }

    // 模糊查詢
    public List<Post> findPostByTitleLike(@RequestParam("postTitle") String postTitle) {
        return postD.findByTitleLike(postTitle);
    }

}
