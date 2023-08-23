package com.team.gallexiv.model;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    final PlanDao planD;

    public PlanService(PlanDao planD){
        this.planD=planD;
    }

    // 取得單筆plan
    public Plan getPlanById(int planId){
        Optional<Plan> plan = planD.findById(planId);
        return plan.orElse(null);
    }

    //取得全部plan
    public List<Plan> getAllPlan(){
        return  planD.findAll();
    }

    //新增貼文
    public Plan insertPlan(Plan plan){
        return planD.save(plan);
    }

    //刪除貼文
    public void deletePlanById(int planId){
        Optional<Plan> planOptional = planD.findById(planId);
        if(planOptional.isEmpty()){
            return;
        }
        planD.deleteById(planId);
    }

    //更新貼文
    public void updatePlanById(int planId, String planName, int planPrice, String planDescription,int planStausNum,String planPicture ){
        Optional<Plan> optional = planD.findById(planId);

        if(optional.isEmpty()){
            return;
        }
        Plan result = optional.get();
        result.setPlanName(planName);
        result.setPlanPrice(planPrice);
//        result.setPlanStatusByStatusId(new Status(17));
        result.setPlanStatusByStatusId(new Status(planStausNum));
        result.setPlanDescription(planDescription);
        result.setPlanPicture(planPicture);
    }

//    public Plan updateBenny(Plan planToupdate){
//
//        planToupdate.getPlanStatusByStatusId().setStatusId(18);
//
//        return planD.save(planToupdate);
//    }

}
