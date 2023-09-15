package com.team.gallexiv.security;

import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.model.UserService;
import com.team.gallexiv.data.model.Userinfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TreeSet;

@Component
public class GallexivUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    String ROLE_PREFIX = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



        VueData rs = userService.getUserByAccountObject(Userinfo.createUserByAccouut(username));
        if(rs.getCode() == 400){
            throw new UsernameNotFoundException("用户名或密碼無效！");
        }
        Userinfo userinfo = (Userinfo) rs.getData();
        return new GallexivAccountUser(userinfo.getUserId(),userinfo.getAccount(),userinfo.getPWord(),new TreeSet<>());
    }

    /**
     * 獲得用戶權限資料（身份組、權限清單）
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Integer userId){

        //菜單操作權限 sys:user:list
        String authority = userService.getUserAuthorityInfo(userId);

        List<GrantedAuthority> userAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);

        //角色(ROLE_admin) 有身份組的話，加入權限字串
        String roleStr = userService.getUserRoleStr(userId);
        if (roleStr != null) {
            userAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + roleStr));
        }

        return userAuthorities;
    }

    public boolean checkJwtClaimValidInRedis(Claims claim){
        String account = claim.getSubject();
        long tokenExp = claim.getExpiration().getTime();
        return userService.checkUserAuthorityInRedis(account,tokenExp);
    }

}
