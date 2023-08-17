package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Objects;

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
    @Basic
    @Column(name = "roleStatus")
    private String roleStatus;
    @JsonManagedReference
    @OneToMany(mappedBy = "accountRoleByRoleId")
    private Collection<RolePermission> rolePermissionsByRoleId;
    @JsonManagedReference
    @OneToMany(mappedBy = "accountRoleByRoleId")
    private Collection<Userinfo> userinfosByRoleId;

}
