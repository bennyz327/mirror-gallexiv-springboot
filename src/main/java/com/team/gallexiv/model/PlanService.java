package com.team.gallexiv.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    final PlanDao planD;
    final PlanForShowDao planForShowD;
    final UserDao userinfoD;
    final StatusDao statusD;

    public PlanService(PlanDao planD, PlanForShowDao planForShowD, UserDao userinfoD,StatusDao statusD) {
        this.planD = planD;
        this.planForShowD = planForShowD;
        this.userinfoD = userinfoD;
        this.statusD = statusD;
    }

    // 取得單筆plan
    public Plan getPlanById(int planId) {
        Optional<Plan> plan = planD.findById(planId);
        return plan.orElse(null);
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

//        Userinfo thisUser = userinfoD.myfindById(ownerId);
        Optional<Userinfo> thisUser = userinfoD.findByUserId(ownerId);


        int thisPlanStatusId = plan.getPlanStatusByStatusId().getStatusId();
        System.out.println("statusID: "+thisPlanStatusId);
        Optional<Status> status = statusD.findById(thisPlanStatusId);


//        if (thisUser != null && status.isPresent()) {
//            plan.setPlanStatusByStatusId(status.get());
//            plan.setOwnerIdByUserId(thisUser);
//            return planD.save(plan);
//        }

        if (status.isPresent() && thisUser.isPresent()) {
            System.out.println("有進去");
            plan.setPlanStatusByStatusId(status.get());
            plan.setOwnerIdByUserId(thisUser.get());
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
