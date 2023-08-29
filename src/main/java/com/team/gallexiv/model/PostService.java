package com.team.gallexiv.model;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

    //取得單筆貼文
    public Post getPostById(Post post) {
        Optional<Post> optionalPost = postD.findById(post.getPostId());
        return optionalPost.orElse(null);
    }

    //取得全部貼文
    public List<Post> getAllPost() {
        return postD.findAll();
    }

    //新增貼文
    public Post insertPost(Post post) {

        Optional<Userinfo> optional = userD.findById(post.getUserinfoByUserId().getUserId());
        if (optional.isPresent()) {

            Optional<Plan> optionalPlan = planD.findById(post.getPlanByPlanId().getPlanId());
            if (optionalPlan.isPresent()) {
                post.setPlanByPlanId(optionalPlan.get());
            }

            Collection<Tag> inputTags = post.getTagsByPostId();
            Collection<Tag> newTags = new ArrayList<>();

            for (Tag inputTag : inputTags) {
                Optional<Tag> TagFindRsOptional = tagD.findByTagName(inputTag.getTagName());

                if (TagFindRsOptional.isPresent()) {
                    newTags.add(TagFindRsOptional.get());
                } else {
                    newTags.add(inputTag);
                }
            }
            post.setTagsByPostId(newTags);

            return postD.save(post);
        }
        return null;
    }

    //刪除貼文
    public String deletePostById(Post post) {
        Optional<Post> postOptional = postD.findById(post.getPostId());
        if (postOptional.isPresent()) {
            postD.deleteById(post.getPostId());
            return "刪除成功";
        }
        return "刪除失敗";
    }

    //更新貼文
    public void updatePostById(Post post) {

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
        }

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

    //模糊查詢
    public List<Post> findPostByTitleLike(@RequestParam("postTitle") String postTitle) {
        return postD.findByTitleLike(postTitle);
    }


}
