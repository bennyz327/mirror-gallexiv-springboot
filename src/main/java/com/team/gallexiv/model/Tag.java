package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "tag", schema = "gallexiv")
public class Tag {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tagId")
    private int tagId;

    @Basic
    @Column(name = "tagName",unique = true)
    private String tagName;

//    @OneToMany(mappedBy = "tagByTagId")
//    private Collection<TagPost> tagPostsByTagId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tagPost", joinColumns = {@JoinColumn(name = "tagId")}, inverseJoinColumns = {@JoinColumn(name = "postId")})
    @JsonIncludeProperties({"postId"})
    private Collection<Post> postsByTagId;


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Tag tag = (Tag) o;
//        return tagId == tag.tagId && Objects.equals(tagName, tag.tagName);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(tagId, tagName);
//    }
//
//    public Collection<TagPost> getTagPostsByTagId() {
//        return tagPostsByTagId;
//    }
//
//    public void setTagPostsByTagId(Collection<TagPost> tagPostsByTagId) {
//        this.tagPostsByTagId = tagPostsByTagId;
//    }
}
