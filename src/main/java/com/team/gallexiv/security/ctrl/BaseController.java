package com.team.gallexiv.security.ctrl;

import com.team.gallexiv.common.utils.JwtUtils;
import com.team.gallexiv.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

//放一些常用的物件類
public class BaseController {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtUtils jwtUtil;

}
