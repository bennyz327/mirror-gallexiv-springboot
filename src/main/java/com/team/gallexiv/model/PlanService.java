package com.team.gallexiv.model;

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
    public Plan getPlanById(Plan plan) {
        Optional<Plan> optionalPlan = planD.findById(plan.getPlanId());
        return optionalPlan.orElse(null);
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
    public Plan insertPlan(Plan plan) {

        Optional<Userinfo> thisUser = userinfoD.findByUserId(plan.getOwnerIdByUserId().getUserId());

        int thisPlanStatusId = plan.getPlanStatusByStatusId().getStatusId();
        System.out.println("statusID: "+thisPlanStatusId);
        Optional<Status> status = statusD.findById(thisPlanStatusId);

        if (status.isPresent() && thisUser.isPresent()) {
            System.out.println("有進去");
            plan.setPlanStatusByStatusId(status.get());
            plan.setOwnerIdByUserId(thisUser.get());
            return planD.save(plan);
        }

        return null;
    }

    //刪除plan
    public String deletePlanById(Plan plan) {
        Optional<Plan> planOptional = planD.findById(plan.getPlanId());
        if (planOptional.isPresent()) {
           planD.deleteById(plan.getPlanId());
           return "刪除成功";
        }
        return "刪除失敗";
    }

    //更新plan
    public Plan updatePlanById(Plan plan) {
        Optional<Plan> optional = planD.findById(plan.getPlanId());

        if (optional.isPresent()) {
            Plan result = optional.get();
            result.setPlanName(plan.getPlanName());
            result.setPlanPrice(plan.getPlanPrice());
            result.setPlanStatusByStatusId(new Status(plan.getPlanStatusByStatusId().getStatusId()));
            result.setPlanDescription(plan.getPlanDescription());
            result.setPlanPicture(plan.getPlanPicture());
            return result;
        }
        return null;

    }

}
