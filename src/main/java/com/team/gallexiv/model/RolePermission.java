package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "rolePermission", schema = "gallexiv")
public class RolePermission {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "RPId")
    private int rpId;


    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "roleId", nullable = false)
    private AccountRole accountRoleByRoleId;


    @ManyToOne
    @JoinColumn(name = "permissionId", referencedColumnName = "permissionId", nullable = false)
    private Permissions permissionsByPermissionId;

}
