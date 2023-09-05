package com.team.gallexiv.data.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRoleDao extends JpaRepository<AccountRole, Integer> {

    public List<AccountRole> findByRoleId(Integer roleId);

}
