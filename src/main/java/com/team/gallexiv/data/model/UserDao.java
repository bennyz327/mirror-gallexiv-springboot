package com.team.gallexiv.data.model;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@DynamicUpdate
public interface UserDao extends JpaRepository<Userinfo, Integer> {

    @Query("select u from Userinfo u where u.userId = ?1")
    Optional<Userinfo> findByUserId(int userId);

    @Query("select u from Userinfo u where u.userId = ?1")
    Userinfo myfindById(Integer id);

    @Query("select u from Userinfo u where u.userName = :userName AND u.pWord = :pWord")
    Userinfo findUserNameAndUserPwd(String userName,String pWord);





}
