package com.team.gallexiv.data.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionsService {
    @Autowired
    PermissionsDao permissionsDao;
    @Autowired
    AccountRoleDao accountRoleDao;
    @Autowired
    RolePermissionsDao rolePermissionsDao;
    @Autowired
    UserDao userDao;

    //回傳單一id的權限，回傳值是一個Id的list
    public List<Integer> getPermissionIDsByUserId(Integer userId) {
        Optional<Userinfo> user = userDao.findById(userId);
        if (user.isPresent()) {
            Userinfo userinfo = user.get();
            AccountRole accountRole = userinfo.getAccountRoleByRoleId();
            Collection<RolePermission> rolePermissions = accountRole.getRolePermissionsByRoleId();
            //將對應的關係轉換成權限lsit
            List<Permissions> permissionsList = rolePermissions.stream().map(RolePermission::getPermissionsByPermissionId).toList();

            //將權限集合轉成ID的list並回傳
            return permissionsList.stream().map(Permissions::getPermissionId).toList();
        }
        //如果沒有找到使用者，回傳null
        return null;
    }

    //回傳單一id的權限，回傳值是一個Permissions的list
    public List<Permissions> getPermissionsByUserId(Integer userId) {
        Optional<Userinfo> user = userDao.findById(userId);
        if (user.isPresent()) {
            Userinfo userinfo = user.get();
            AccountRole accountRole = userinfo.getAccountRoleByRoleId();
            Collection<RolePermission> rolePermissions = accountRole.getRolePermissionsByRoleId();
            //將對應的關係轉換成權限lsit並回傳
            return rolePermissions.stream().map(RolePermission::getPermissionsByPermissionId).toList();
        }
        //如果沒有找到使用者，回傳null
        return null;
    }
}
