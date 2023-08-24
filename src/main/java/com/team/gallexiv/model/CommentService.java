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
    public Comment getPlanById(int commentId) {
        Optional<Comment> comment = commentD.findById(commentId);
        return comment.orElse(null);
    }

    public PlanForShow getPlanForShowById(int planId) {
        Optional<PlanForShow> plan = planForShowD.findById(planId);
        return plan.orElse(null);
    }

    //取得全部plan
    public List<Plan> getAllPlan() {
        return planD.findAll();
    }

    public List<PlanForShow> getAllPlanForShow() {
        return planForShowD.findAll();
    }

    //新增plan
    public Plan insertPlan(int ownerId,Plan plan) {

        Userinfo thisUser = userinfoD.myfindById(ownerId);

        Status thisPlanStatusObject = plan.getPlanStatusByStatusId();
        int thisPlanStatusId = plan.getPlanId();

//        System.out.println("查詢條件ID"+thisPlanStatusObject.getStatusId());
//        Status status = statusD.findByStatusName(thisPlanStatusObject.getStatusCategory(),thisPlanStatusObject.getStatusName());
//        System.out.println(status);

//        Status status = statusD.getById(thisPlanStatusId);
//        plan.setPlanStatusByStatusId(status);

        if (thisUser!=null) {
            plan.setOwnerIdByUserId(thisUser);
            return planD.save(plan);
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
