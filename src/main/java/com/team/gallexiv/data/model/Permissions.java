package com.team.gallexiv.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Objects;

@Setter
@Getter
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

    @OneToMany(mappedBy = "permissionsByPermissionId")
    @JsonIncludeProperties({"rpId","accountRoleByRoleId"})
    private Collection<RolePermission> rolePermissionsByPermissionId;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Permissions that = (Permissions) o;
//        return permissionId == that.permissionId && Objects.equals(permissionName, that.permissionName);
//    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionId, permissionName);
    }

    public void setRolePermissionsByPermissionId(Collection<RolePermission> rolePermissionsByPermissionId) {
        this.rolePermissionsByPermissionId = rolePermissionsByPermissionId;
    }
}
