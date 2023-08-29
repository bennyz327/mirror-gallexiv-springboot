package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "picture", schema = "gallexiv")
public class Picture {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pictureId", nullable = false)
    private int pictureId;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "imgUploadTime")
    private Timestamp imgUploadTime;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "imgPath")
    private String imgPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_status", referencedColumnName = "code_id")
    private Status pictureStatusByStatusId;

    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    @JsonIncludeProperties({"postId"})
    private Post postByPostId;

}
