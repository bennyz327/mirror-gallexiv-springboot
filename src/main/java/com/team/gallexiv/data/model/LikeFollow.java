package com.team.gallexiv.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "likeFollow")
public class LikeFollow {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "likeId")
    private int likeId;

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId",nullable = false)
    private Userinfo userByUserId;

    @ManyToOne
    @JoinColumn(name = "postId",referencedColumnName = "postId",nullable = false)
    @JsonIncludeProperties({"postId"})
    private Post postByPostId;

    @Basic
    @Column(name = "type")
    private String type;

}
