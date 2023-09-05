package com.team.gallexiv.data.model;

import java.util.List;
import java.util.Optional;

public class AccountRoleService {

    final AccountRoleDao accountRoleD;

    public AccountRoleService(AccountRoleDao accountRoleD){
        this.accountRoleD =accountRoleD;
    }

    //更改status
    public void updateStatus(AccountRole role){
        Optional<AccountRole> optional =accountRoleD.findById(role.getRoleId());

        if(optional.isPresent()){
            AccountRole result = optional.get();
            result.setRoleStatusByStatusId(new Status(6));
        }

    }

    public List<AccountRole> getAccountRolesByUserId(Integer userId){
        return accountRoleD.findByRoleId(userId);
    }
}
