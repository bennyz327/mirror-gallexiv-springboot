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
    @JsonIgnore
    @OneToMany(mappedBy = "tagByTagId")
    private Collection<TagPost> tagPostsByTagId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tagPost", joinColumns = {@JoinColumn(name = "tagId")}, inverseJoinColumns = {@JoinColumn(name = "postId")})
    @JsonIncludeProperties({"postId"})
    private Collection<Post> postsByTagId;
}
