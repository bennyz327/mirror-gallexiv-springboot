package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "accountRole", schema = "gallexiv")
public class AccountRole {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roleId")
    private int roleId;

    @Basic
    @Column(name = "roleName")
    private String roleName;

    @JsonIgnore
    @OneToMany(mappedBy = "accountRoleByRoleId")
    private Collection<RolePermission> rolePermissionsByRoleId;

    @JsonIgnore
    @OneToMany(mappedBy = "accountRoleByRoleId")
    private Collection<Userinfo> userinfosByRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_status", referencedColumnName = "code_id")
    @JsonIncludeProperties("statusName")
    private Status roleStatusByStatusId;


}
