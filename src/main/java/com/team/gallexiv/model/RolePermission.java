package com.team.gallexiv.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
@Table(name = "rolePermission", schema = "gallexiv")
public class RolePermission {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "RPId")
    private int rpId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "roleId", nullable = false)
    private AccountRole accountRoleByRoleId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "permissionId", referencedColumnName = "permissionId", nullable = false)
    private Permissions permissionsByPermissionId;

    public void setRpId(int rpId) {
        this.rpId = rpId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return rpId == that.rpId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rpId);
    }

    public void setAccountRoleByRoleId(AccountRole accountRoleByRoleId) {
        this.accountRoleByRoleId = accountRoleByRoleId;
    }

    public void setPermissionsByPermissionId(Permissions permissionsByPermissionId) {
        this.permissionsByPermissionId = permissionsByPermissionId;
    }
}
