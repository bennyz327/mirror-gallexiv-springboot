package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "permissions", schema = "gallexiv")
public class Permissions {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "permissionId")
    private int permissionId;
    @Basic
    @Column(name = "permissionName")
    private String permissionName;
    @JsonIgnore
    @OneToMany(mappedBy = "permissionsByPermissionId")
    private Collection<RolePermission> rolePermissionsByPermissionId;

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permissions that = (Permissions) o;
        return permissionId == that.permissionId && Objects.equals(permissionName, that.permissionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionId, permissionName);
    }

    public Collection<RolePermission> getRolePermissionsByPermissionId() {
        return rolePermissionsByPermissionId;
    }

    public void setRolePermissionsByPermissionId(Collection<RolePermission> rolePermissionsByPermissionId) {
        this.rolePermissionsByPermissionId = rolePermissionsByPermissionId;
    }
}
