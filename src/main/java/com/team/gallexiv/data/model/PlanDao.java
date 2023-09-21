package com.team.gallexiv.data.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanDao extends JpaRepository<Plan, Integer> {

    // @Query("from Plan where userId = :UserId")
    // List<Plan> getAllPlanByUserId(@Param("userId") Integer UserId);

    public List<Plan> findByOwnerIdByUserId(Userinfo userinfo);

    public Plan findByPlanStatusByStatusId(Status status);

    // 在 user 設定頁面取得 plan (透過 userId & status = 17)
    @Query("SELECT p FROM Plan p WHERE p.ownerIdByUserId.userId = :userId AND p.planStatusByStatusId.statusId = 17")
    public List<Plan> findPlanByUserIdAndStatus(@Param("userId") Integer userId);

}
