package com.team.gallexiv.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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


    @OneToMany(mappedBy = "accountRoleByRoleId")
    @JsonIncludeProperties({"rpId","permissionsByPermissionId"})
    private Collection<RolePermission> rolePermissionsByRoleId;


    @OneToMany(mappedBy = "accountRoleByRoleId")
    @JsonIncludeProperties({"userId","userName"})
    private Collection<Userinfo> userInfosByRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_status", referencedColumnName = "code_id")
    @JsonIncludeProperties({"statusId","statusType","statusCategory","statusName"})
    private Status roleStatusByStatusId;


}
