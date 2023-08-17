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

//    public int getTagId() {
//        return tagId;
//    }
//
//    public void setTagId(int tagId) {
//        this.tagId = tagId;
//    }
//
//    public String getTagName() {
//        return tagName;
//    }
//
//    public void setTagName(String tagName) {
//        this.tagName = tagName;
//    }
//
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
