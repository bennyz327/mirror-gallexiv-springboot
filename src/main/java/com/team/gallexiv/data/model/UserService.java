package com.team.gallexiv.data.model;

import com.team.gallexiv.common.lang.VueData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    final UserDao userD;
    final PostDao postD;
    final UserSubscriptionDao userSubD;
    final PlanDao planD;
    final StatusDao statusD;
    final CommentDao commentD;
    final UserSubscriptionDao userSubscriptionD;
    final AccountRoleDao accountRoleD;

    public UserService(UserDao userD,CommentDao commentD,PostDao postD,UserSubscriptionDao userSubD,PlanDao planD,StatusDao statusD,UserSubscriptionDao userSubscriptionD,AccountRoleDao accountRoleD) {
        this.userD = userD;
        this.commentD = commentD;
        this.postD = postD;
        this.planD = planD;
        this.userSubD = userSubD;
        this.statusD = statusD;
        this.userSubscriptionD = userSubscriptionD;
        this.accountRoleD = accountRoleD;
    }

    public Userinfo mygetUserById(int id) {
        return userD.myfindById(id);
    }

    // public Userinfo getUserById(int id) {
    // Optional<Userinfo> post = userD.findById(id);
    // return post.orElse(null);
    // }

    // 取得登入資料帳號密碼
    public VueData checkLogin(Userinfo user){
        Userinfo userInfo = userD.findUserNameAndUserPwd(user.getUserName(), user.getPWord());

        if(userInfo != null){
            return VueData.ok(userInfo);
        }
        return VueData.error("帳號或密碼有誤");
    }

    // 取得單筆user OK
    public VueData getUserById(int userId) {
        Optional<Userinfo> optionalUserinfo = userD.findById(userId);
        if (optionalUserinfo.isPresent()) {
            return VueData.ok(optionalUserinfo.orElse(null));
        }
        return VueData.error("查詢失敗");
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
        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i).getUserName());
            if (userName.equals(a.get(i).getUserName())) {
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
}

