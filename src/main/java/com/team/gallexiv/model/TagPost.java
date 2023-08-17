package com.team.gallexiv.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tagPost", schema = "gallexiv")
public class TagPost {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tagPostId")
    private int tagPostId;
    @ManyToOne
    @JoinColumn(name = "tagId", referencedColumnName = "tagId", nullable = false)
    private Tag tagByTagId;
    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId", nullable = false)
    private Post postByPostId;

//    public int getTagPostId() {
//        return tagPostId;
//    }
//
//    public void setTagPostId(int tagPostId) {
//        this.tagPostId = tagPostId;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TagPost tagPost = (TagPost) o;
//        return tagPostId == tagPost.tagPostId;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(tagPostId);
//    }

//    public Tag getTagByTagId() {
//        return tagByTagId;
//    }
//
//    public void setTagByTagId(Tag tagByTagId) {
//        this.tagByTagId = tagByTagId;
//    }
//
//    public Post getPostByPostId() {
//        return postByPostId;
//    }
//
//    public void setPostByPostId(Post postByPostId) {
//        this.postByPostId = postByPostId;
//    }
}
