package com.team.gallexiv.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanForShowDao extends JpaRepository<PlanForShow, Integer> {



}
