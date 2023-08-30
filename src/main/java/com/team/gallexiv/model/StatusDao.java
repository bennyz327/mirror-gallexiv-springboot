package com.team.gallexiv.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusDao extends JpaRepository<Status, Integer> {
    @Query("select cm from Status cm where cm.statusName = ?2 AND cm.statusCategory = ?1")
    Status findByStatusName(String statusCategory,String statusName);

}
