package com.team.gallexiv.data.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanDao extends JpaRepository<Plan, Integer> {

//    @Query("from Plan where userId = :UserId")
//    List<Plan> getAllPlanByUserId(@Param("userId") Integer UserId);



}
