package com.team.gallexiv.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "link_mapping")
public class LinkMapping {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "link_id")
    private int linkId;
    @Basic
    @Column(name = "link_site")
    private String linkSite;
    @Basic
    @Column(name = "link_source")
    private String linkSource;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Userinfo userinfoByUserId;


}
