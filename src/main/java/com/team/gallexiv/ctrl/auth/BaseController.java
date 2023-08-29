package com.team.gallexiv.ctrl.auth;

import com.team.gallexiv.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    RedisUtil redisUtil;

}
