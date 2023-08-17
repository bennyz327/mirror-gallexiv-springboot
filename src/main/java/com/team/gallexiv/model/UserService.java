package com.team.gallexiv.model;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    final UserDao userDao;

    public UserService(UserDao userD) {
        this.userDao = userD;
    }

    public Userinfo getUserById(int id) {
        Optional<Userinfo> post = userDao.findById(id);
        return post.orElse(null);
    }
    public Userinfo mygetUserById(int id) {
        return userDao.myfindById(id);
    }
}
