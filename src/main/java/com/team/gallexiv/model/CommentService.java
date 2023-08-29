package com.team.gallexiv.model;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    final PlanDao planD;
    final PlanForShowDao planForShowD;

    final CommentDao commentD;
    final UserDao userinfoD;
    final StatusDao statusD;

    public CommentService(PlanDao planD, PlanForShowDao planForShowD, UserDao userinfoD, StatusDao statusD,CommentDao commentD) {
        this.planD = planD;
        this.planForShowD = planForShowD;
        this.userinfoD = userinfoD;
        this.statusD = statusD;
        this.commentD =commentD;
    }

    // 取得單筆comment
    public Comment getCommentById(Comment comment) {
        Optional<Comment> optionalComment = commentD.findById(comment.getCommentId());
        return optionalComment.orElse(null);
    }

    //取得全部comment
    public List<Comment> getAllComment() {
        return commentD.findAll();
    }

    //新增comment
    public Comment insertComment(Comment comment) {

        Optional<Userinfo> thisUser = userinfoD.findByUserId(comment.getUserinfoByUserId().getUserId());

        int thisCommentStatusId = comment.getCommentStatusByStatusId().getStatusId();
        System.out.println("statusID: "+thisCommentStatusId);
        Optional<Status> status = statusD.findById(thisCommentStatusId);

        if (status.isPresent() && thisUser.isPresent()) {
            System.out.println("有進去");
            comment.setCommentStatusByStatusId(status.get());
            comment.setUserinfoByUserId(thisUser.get());
            return commentD.save(comment);
        }

        return null;
    }

    //刪除plan
    public void deletePlanById(int planId) {
        Optional<Plan> planOptional = planD.findById(planId);
        if (planOptional.isEmpty()) {
            return;
        }
        planD.deleteById(planId);
    }

    //更新plan
    public void updatePlanById(int planId, String planName, int planPrice, String planDescription, int planStatusNum, String planPicture) {
        Optional<Plan> optional = planD.findById(planId);

        if (optional.isEmpty()) {
            return;
        }
        Plan result = optional.get();
        result.setPlanName(planName);
        result.setPlanPrice(planPrice);
        result.setPlanStatusByStatusId(new Status(planStatusNum));
        result.setPlanDescription(planDescription);
        result.setPlanPicture(planPicture);
    }

//    public Plan updateBenny(Plan planToupdate){
//
//
//        return planD.save(planToupdate);
//    }

}
