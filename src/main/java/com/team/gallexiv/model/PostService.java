package com.team.gallexiv.model;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    final PostDao postD;
    final UserDao userD;

    public PostService(PostDao postD, UserDao userD) {
        this.postD = postD;
        this.userD = userD;
    }

    public Post getPostById(int id) {
        Optional<Post> post = postD.findById(id);
        return post.orElse(null);
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
                System.out.println("這是查到的使用者ID="+ownerUser.getUserId());
                return ownerUser;
            }
            System.out.println("查無貼文作者");
            return null;
        }
        System.out.println("查無貼文資料");
        return null;
    }
}
