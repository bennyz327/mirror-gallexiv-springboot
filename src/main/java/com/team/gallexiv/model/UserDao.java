package com.team.gallexiv.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<Userinfo, Integer> {

    @Query("select u from Userinfo u where u.userId = ?1")
    Optional<Userinfo> findByUserId(int userId);

    @Query("select u from Userinfo u where u.userId = ?1")
    Userinfo myfindById(Integer id);
}
