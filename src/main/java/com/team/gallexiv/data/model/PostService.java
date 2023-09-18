package com.team.gallexiv.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
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

    // 取得單筆貼文 使用 RequestParam
    public VueData getPostByIdUseParam(Integer postId) {
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
    public String insertPost(Map<String, String> props) throws JsonProcessingException {
        String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Userinfo> optionalUserinfo = userD.findByAccount(accoutName);

        if (optionalUserinfo.isPresent()) {
            for (String key : props.keySet()) {

                System.out.println(key + " : " + props.get(key));

                String[] tags = props.get("tags").split(",");
                Collection<String> inputTags = Arrays.asList(tags);
                Collection<Tag> newTags = new ArrayList<>();

                System.out.println(inputTags);

                for (String inputTag : inputTags) {
                    Optional<Tag> TagFindRsOptional = tagD.findByTagName(inputTag);
                    if (TagFindRsOptional.isPresent()) {
                        newTags.add(TagFindRsOptional.get());
                    } else {
                        newTags.add(new Tag(inputTag));
                    }
                }

                System.out.println(newTags.toString());

                //組裝Post Entity
                Post newPost = new Post(optionalUserinfo.get(), props.get("title"), props.get("description"), newTags);
                //取得Plan Entity 塞入
                // TODO planId null 判斷
                if (planD.findById(Integer.parseInt(props.get("planId"))).isPresent()) {
                    log.info("找到Plan Entity");
                    newPost.setPlanByPlanId(planD.findById(Integer.parseInt(props.get("planId"))).get());
                }
                newPost.setPostPublic(Integer.parseInt(props.get("isPublic")));
                newPost.setPostAgeLimit(Integer.parseInt(props.get("nsfw")));
                newPost.setPostStatusByStatusId(new Status(7));
                log.info("Post 組裝完成");

                return String.valueOf(postD.save(newPost).getPostId());
            }
            return "新增失敗";
        }
        return "查無使用者";
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

        log.info("getId " + post.getPostId());
        String accountName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Userinfo> optionalUserinfo = userD.findByAccount(accountName);
        Optional<Post> optional = postD.findById(post.getPostId());

        if (optionalUserinfo.isPresent() && optional.isPresent()) {
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

    public VueData getPostByUserId(int userId) {

        Optional<Userinfo> userinfo = userD.findByUserId(userId);
        if (userinfo.isPresent()) {
            return VueData.ok(postD.findByUserinfoByUserId(userinfo.get()));
        }
        return VueData.error("查無該使用者貼文");
    }

    // 模糊查詢
    public List<Post> findPostByTitleLike(@RequestParam("postTitle") String postTitle) {
        return postD.findByTitleLike(postTitle);
    }

    public VueData findPostWithPlan(){
        String accountName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Userinfo> optionalUserinfo = userD.findByAccount(accountName);
        if (optionalUserinfo.isPresent()){
            int userId = optionalUserinfo.get().getUserId();
            return VueData.ok(postD.postWithPlan(userId));
        }
        return VueData.error("查無此使用者");
    }
    public VueData findPostNotWithPlan(){
        String accountName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Userinfo> optionalUserinfo = userD.findByAccount(accountName);
        if (optionalUserinfo.isPresent()){
            int userId = optionalUserinfo.get().getUserId();
            return VueData.ok(postD.postWithNoPlan(userId));
        }
        return VueData.error("查無此使用者");
    }

}
