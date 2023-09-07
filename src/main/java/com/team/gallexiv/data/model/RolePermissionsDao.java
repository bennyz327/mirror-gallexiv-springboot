package com.team.gallexiv.data.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionsDao extends JpaRepository<RolePermission, Integer> {
    List<RolePermission> findByPermissionsByPermissionId_PermissionId(int permissionId);
    RolePermission findByAccountRoleByRoleId_UserInfosByRoleId_UserId(int userId);



}
