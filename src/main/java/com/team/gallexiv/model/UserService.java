package com.team.gallexiv.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

    public UserService(UserDao userD,CommentDao commentD,PostDao postD,UserSubscriptionDao userSubD,PlanDao planD,StatusDao statusD) {
        this.userD = userD;
        this.commentD = commentD;
        this.postD = postD;
        this.planD = planD;
        this.userSubD = userSubD;
        this.statusD = statusD;
    }

    public Userinfo mygetUserById(int id) {
        return userD.myfindById(id);
    }


//    public Userinfo getUserById(int id) {
//        Optional<Userinfo> post = userD.findById(id);
//        return post.orElse(null);
//    }


    //取得單筆user OK
    public Userinfo getUserById(Userinfo user) {
        Optional<Userinfo> optionalUserinfo = userD.findById(user.getUserId());
        return optionalUserinfo.orElse(null);
    }

    //取得所有user
    public List<Userinfo> getAllUsers(){
        return userD.findAll();
    }

    //新增使用者
    public Userinfo insertUser(Userinfo user) {

        String userName =user.getAccount();
        List<Userinfo> a = userD.findAll();
        for(int i=0;i<a.size();i++){
            System.out.println(a.get(i).getUserName());
            if(userName.equals(a.get(i).getUserName())){
                System.out.println("帳號重複");
                return null;
            }
        }
        return userD.save(user);
    }

    //刪除user 少判斷
    public Userinfo unableUserById(Userinfo user) {
//        Optional<Comment> optionalComment= commentD.findById(user.getUserId());
//        Comment result = optionalComment.get();
//        result.getCommentId();

        Optional<Userinfo> optional =userD.findById(user.getUserId());

        if(optional.isPresent()){
            Optional<Status> optionalStatus = statusD.findById(user.getUserStatusByStatusId().getStatusId());
            Status resultStatus = optionalStatus.get();

            Userinfo result = optional.get();
            result.setUserStatusByStatusId(resultStatus);
            return user;
        }

//       if(user.getUserStatusByStatusId().getStatusName().equals("DELETED") || user.getUserStatusByStatusId().getStatusName().equals("UNACTIVE")  ){
//            Optional<Plan> a = planD.findById(user.getUserId());
//            a.get().setPlanStatusByStatusId(comstatus);
//       }
        return null;
    }

    //更新user
    public void updateUserById(Userinfo user) {
        Optional<Userinfo> optional = userD.findById(user.getUserId());

        System.out.println("要更新資料: "+user);
        if (optional.isPresent()) {
            System.out.println("找到存在的使用者");
            Userinfo result = optional.get();
            System.out.println(result);
            result.setUserName(user.getUserName() != null ? user.getUserName() : result.getUserName());
            result.setAccount(user.getAccount() != null ? user.getAccount() : result.getAccount());
            result.setPWord(user.getPWord() != null ? user.getPWord() : result.getPWord());

            result.setUserEmail(user.getUserEmail() != null ? user.getUserEmail() : result.getUserEmail());
            result.setEmail_verified(user.getEmail_verified() != null ? user.getEmail_verified() : result.getEmail_verified());
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
        }
        System.out.println("結束SERVICE");
    }
}

