package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "tagName")
    private String tagName;
    @JsonIgnore
    @OneToMany(mappedBy = "tagByTagId")
    private Collection<TagPost> tagPostsByTagId;

}
