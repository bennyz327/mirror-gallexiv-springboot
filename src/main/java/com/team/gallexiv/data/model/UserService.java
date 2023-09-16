package com.team.gallexiv.data.model;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.system.UserInfo;
import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.common.utils.RedisUtil;
import com.team.gallexiv.data.dto.PreRegisterUserinfo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.team.gallexiv.common.lang.Const.JWT_EXPIRE_SECONDS;

@Slf4j
@Service
@Transactional
public class UserService {

    final UserDao userD;
    final PostDao postD;
    final UserSubscriptionDao userSubD;
    final PlanDao planD;
    final StatusDao statusD;
    final CommentDao commentD;
    final UserSubscriptionDao userSubscriptionD;
    final AccountRoleDao accountRoleD;
    final RedisUtil redisUtil;
    final EntityManager entityManager;
    final PermissionsService permissionsService;
    final RolePermissionsDao rolePermissionD;
    private final BCryptPasswordEncoder bCryptPE;

    @Autowired
    public UserService(UserDao userD, CommentDao commentD, PostDao postD, UserSubscriptionDao userSubD, PlanDao planD, StatusDao statusD, UserSubscriptionDao userSubscriptionD, AccountRoleDao accountRoleD, RedisUtil redisUtil, EntityManager entityManager,
                       PermissionsService permissionsS, RolePermissionsDao rolePermissionD, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userD = userD;
        this.commentD = commentD;
        this.postD = postD;
        this.planD = planD;
        this.userSubD = userSubD;
        this.statusD = statusD;
        this.userSubscriptionD = userSubscriptionD;
        this.accountRoleD = accountRoleD;
        this.redisUtil = redisUtil;
        this.entityManager = entityManager;
        this.permissionsService = permissionsS;
        this.rolePermissionD = rolePermissionD;
        this.bCryptPE = bCryptPasswordEncoder;
    }

    public Userinfo mygetUserById(int id) {
        return userD.myfindById(id);
    }

    // public Userinfo getUserById(int id) {
    // Optional<Userinfo> post = userD.findById(id);
    // return post.orElse(null);
    // }

    // 取得登入資料帳號密碼
//    public VueData checkLogin(Userinfo user){
//        Userinfo userInfo = userD.findUserNameAndUserPwd(user.getUserName(), user.getPWord());
//
//        if(userInfo != null){
//            return VueData.ok(userInfo);
//        }
//        return VueData.error("帳號或密碼有誤");
//    }

    // 取得單筆user OK
    public VueData getUserById(int userId) {
        Optional<Userinfo> optionalUserinfo = userD.findById(userId);
        if (optionalUserinfo.isPresent()) {
            return VueData.ok(optionalUserinfo.orElse(null));
        }
        return VueData.error("查詢失敗");
    }

    public VueData getUserById() {
        String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Userinfo> optionalUserinfo = userD.findByAccount(accoutName);
        if (optionalUserinfo.isPresent()) {
            return VueData.ok(optionalUserinfo.orElse(null));
        }
        return VueData.error("查詢失敗");
    }

    public VueData getUserByAccountObject(Userinfo user) {
        Optional<Userinfo> optionalUserinfo = userD.findByAccount(user.getAccount());
        if (optionalUserinfo.isPresent()) {
            return VueData.ok(optionalUserinfo.orElse(null));
        }
        return VueData.error("查詢帳號失敗");
    }

    public Userinfo getUserByAccount(String accout) {
        Optional<Userinfo> optionalUserinfo = userD.findByAccount(accout);
        if (optionalUserinfo.isPresent()) {
            return optionalUserinfo.orElse(null);
        }
        return null;
    }

    // 取得所有user
    public VueData getAllUsers() {
        List<Userinfo> result = userD.findAll();
        if (result.isEmpty()) {
            return VueData.error("查詢失敗");
        }
        return VueData.ok(result);
    }

    // 新增使用者-----先略過不改成VueData
    public Userinfo insertUser(Userinfo user) {

        String userName = user.getAccount();
        List<Userinfo> a = userD.findAll();
        for (Userinfo userinfo : a) {
            System.out.println(userinfo.getAccount());
            if (userName.equals(userinfo.getAccount())) {
                System.out.println("帳號重複");
                return null;
            }
        }
        return userD.save(user);
    }

    // 刪除user 少判斷
    public VueData unableUserById(Userinfo user) {
        // Optional<Comment> optionalComment = commentD.findById(user.getUserId());
        // Status resultComment = optionalComment.get().getCommentStatusByStatusId();

        // Optional<Post> optionalPost = postD.findById(user.getUserId());
        // Status resultPost = optionalPost.get().getPostStatusByStatusId();

        // Optional<AccountRole> optionalAccountRole =
        // accountRoleD.findById(user.getUserId());
        // Status resultAccountRole =
        // optionalAccountRole.get().getRoleStatusByStatusId();

        // Optional<Plan> optionalPlan = planD.findById(user.getUserId());
        // Status resultPlan = optionalPlan.get().getPlanStatusByStatusId();

        Optional<Userinfo> optionalUserinfo = userD.findById(user.getUserId());
        if (optionalUserinfo.isPresent()) {
            Optional<Status> optionalStatus = statusD.findById(user.getUserStatusByStatusId().getStatusId());
            Status resultStatus = optionalStatus.get();

            Userinfo result = optionalUserinfo.get();
            result.setUserStatusByStatusId(resultStatus);
            // return user;
            return VueData.ok(user);
        }

        // 無法顯示 VueData 返回結果
        return VueData.error("更改狀態失敗");
    }

    // 更新user
    public VueData updateUserById(Userinfo user) {
        Optional<Userinfo> optionalUserinfo = userD.findById(user.getUserId());

        System.out.println("要更新資料: " + user);
        if (optionalUserinfo.isPresent()) {
            System.out.println("找到存在的使用者");
            Userinfo result = optionalUserinfo.get();
            System.out.println(result);
            result.setUserName(user.getUserName() != null ? user.getUserName() : result.getUserName());
            result.setAccount(user.getAccount() != null ? user.getAccount() : result.getAccount());
            result.setPWord(user.getPWord() != null ? user.getPWord() : result.getPWord()); // ---暫時無法更改

            result.setUserEmail(user.getUserEmail() != null ? user.getUserEmail() : result.getUserEmail());
            result.setEmail_verified(
                    user.getEmail_verified() != null ? user.getEmail_verified() : result.getEmail_verified());
            result.setBirthday(user.getBirthday() != null ? user.getBirthday() : result.getBirthday());
            result.setGender(user.getGender() != null ? user.getGender() : result.getGender());
            result.setAvatar(user.getAvatar() != null ? user.getAvatar() : result.getAvatar());
            result.setIntro(user.getIntro() != null ? user.getIntro() : result.getIntro());
            result.setAccountRoleByRoleId(user.getAccountRoleByRoleId() != null ? user.getAccountRoleByRoleId() : result.getAccountRoleByRoleId());
            result.setUserStatusByStatusId(user.getUserStatusByStatusId() != null ? user.getUserStatusByStatusId() : result.getUserStatusByStatusId());
            result.setFirst_name(user.getFirst_name() != null ? user.getFirst_name() : result.getFirst_name());
            result.setLast_name(user.getLast_name() != null ? user.getLast_name() : result.getLast_name());
            result.setModified_by(user.getModified_by() != null ? user.getModified_by() : result.getModified_by());
            userD.save(result);
            return VueData.ok(result);
        }
        System.out.println("結束SERVICE");
        return VueData.error("更新失敗");
    }

    //查詢權限字串並放入redis緩存
    public String getUserAuthorityInfo(Integer userId) {

        Session session = entityManager.unwrap(Session.class);

        Userinfo sysUser = session.find(Userinfo.class, userId);

        String authority = "";

        if (redisUtil.hasKey("GrantedAuthority:" + sysUser.getAccount())) {
            log.info("從Redis獲取權限字串並刷新");
            redisUtil.expire("GrantedAuthority:" + sysUser.getAccount(), JWT_EXPIRE_SECONDS);
            authority = (String) redisUtil.get("GrantedAuthority:" + sysUser.getAccount());
            log.info("權限字串：{}", authority);

        } else {
            log.info("緩存無資料，準備從資料庫獲取權限字串");

            // 獲得權限ID清單
            List<Permissions> pIds = permissionsService.getPermissionsByUserId(userId);

//            if (!pIds.isEmpty()) {
//                List<Permissions> menus = session.createQuery("from Permissions where permissionId in :menuIds", Permissions.class)
//                        .setParameter("pIds", pIds)
//                        .getResultList();
//                String menuPerms = menus.stream().map(Permissions::getPerms).collect(Collectors.joining(","));
//                authority = authority.concat(menuPerms);
//            }
            if (!pIds.isEmpty()) {
                String perms = pIds.stream().map(Permissions::getPermissionName).collect(Collectors.joining(","));
                log.info("找到角色權限：{}", perms);
                authority = authority.concat(perms);
                log.info("組合權限字串後為：{}", authority);
            }

            redisUtil.set("GrantedAuthority:" + sysUser.getAccount(), authority, JWT_EXPIRE_SECONDS);
        }

        log.info("角色權限字串：{}", authority);
        return authority;
    }

    public String getUserRoleStr(Integer userId) {
        Optional<Userinfo> user = userD.findById(userId);
        if (user.isPresent()) {
            String roleName = String.valueOf(user.get().getAccountRoleByRoleId().getRoleName());
            if (!roleName.isEmpty()) {
                log.info("獲取的身份組為空");
                return null;
            }
            log.info("找到所屬身份，身份組為：{}", roleName);
            return roleName;
        }
        log.info("找不到使用者，無法獲取身份組");
        return null;
    }
    public String getUserRoleStrByUserEntity(Userinfo user) {
        log.info(user.getAccountRoleByRoleId().toString());
        String roleName = user.getAccountRoleByRoleId().getRoleName();
        if (roleName.isEmpty()) {
            log.info("獲取的身份組為空");
            return null;
        }
        log.info("找到所屬身份，身份組為：{}", roleName);
        return roleName;
    }

    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:" + username);
        redisUtil.del("RefreshExpire:" + username);
    }

    public void clearUserAuthorityInfoByRoleId(Integer roleId) {
        AccountRole role = accountRoleD.findById(roleId).get();
        List<Userinfo> userlist = userD.findByAccountRoleByRoleId(role);
        log.info("即將清除身份ID為 {} 的使用者 {}", roleId, userlist);
        for (Userinfo user : userlist) {
            redisUtil.del("GrantedAuthority:" + user.getAccount());
        }
    }

    public void clearUserAuthorityInfoByPermissionId(Integer permissionId) {
        List<RolePermission> role_permission_list = rolePermissionD.findByPermissionsByPermissionId_PermissionId(permissionId);
        List<Collection<Userinfo>> roleUserMap = role_permission_list.stream().map(RolePermission::getAccountRoleByRoleId).map(AccountRole::getUserInfosByRoleId).toList();
        List<Userinfo> userlist = roleUserMap.stream().flatMap(Collection::stream).toList();
        log.info("即將清除權限ID {} 的使用者 {}", permissionId, userlist);
        for (Userinfo user : userlist) {
            redisUtil.del("GrantedAuthority:" + user.getAccount());
        }
    }

    public boolean checkUserAuthorityInRedis(String account, long tokenExpTime) {
        //查詢redis
        if (redisUtil.hasKey("GrantedAuthority:" + account)) {
            long redisExpTime = (long) redisUtil.get("RefreshExpire:" + account);
            log.info("Redis緩存的過期時間：{}", redisExpTime);
            log.info("Token過期的時間：{}", tokenExpTime);
            //因為有誤差，需加上2000毫秒來保證token過期時間在redis過期時間的後面
            if (redisExpTime < (tokenExpTime + 2000)) {
                log.info("找到有效緩存資料");
                String authority = (String) redisUtil.get("GrantedAuthority:" + account);
                log.info("從Redis獲取權限字串");
                log.info("登入帳號為 {}", account);
                log.info("權限字串：{}", authority);
                return true;
            }
            log.info("認證已棄用，判定為無權限");
            return false;
        } else {
            log.info("緩存無資料，判定為無權限");
            return false;
        }
    }

    public Userinfo getUserByEmail(String email) {
        Optional<Userinfo> optionalUserinfo = userD.findByUserEmail(email);
        if (optionalUserinfo.isPresent()) {
            return optionalUserinfo.orElse(null);
        }
        return null;
    }

    public PreRegisterUserinfo createAndAddPreRegisterUser(OAuth2User user) {
        log.info("創建預註冊使用者");
        Userinfo new_user = new Userinfo();
        new_user.setUserName(String.valueOf(user.getAttributes().get("name")));
        log.info("帳號名是" + user.getName());
        new_user.setAccount(user.getName());
        String randomPassword = RandomUtil.randomString(8);
        new_user.setPWord(bCryptPE.encode(randomPassword));
        new_user.setUserEmail(String.valueOf(user.getAttributes().get("email")));
        new_user.setAccountRoleByRoleId(accountRoleD.findById(3).get());
        new_user.setUserStatusByStatusId(statusD.findById(5).get());
        log.info("保存預註冊使用者資料");
        userD.save(new_user);
        log.info("返回生成的隨機密碼請用戶更改");
        return new PreRegisterUserinfo(String.valueOf(user.getAttributes().get("name")), randomPassword, String.valueOf(user.getAttributes().get("email")));
    }
    public Userinfo getUserEntityById (int userId){
        Optional<Userinfo> optionalUserinfo = userD.findById(userId);
        if (optionalUserinfo.isPresent()) {
            return optionalUserinfo.orElse(null);
        }
        return null;
    }

}

