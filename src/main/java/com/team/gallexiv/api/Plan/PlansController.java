package com.team.gallexiv.api.Plan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team.gallexiv.model.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlansController {

    final PlanService planS;

    final UserService userS;

    @Autowired
    public PlansController(PlanService planS, UserService userS) {
        this.planS = planS;
        this.userS = userS;
    }



    @GetMapping(path = "/plans/{planId}", produces = "application/json")
    @Operation(description = "取得單筆plan (GET BY ID)")
    public Plan getPlanById(@PathVariable int planId) {
        return planS.getPlanById(planId);
    }

    @PostMapping(path = "/plans/insert",produces = "application/json;charset=UTF-8")
    @Operation(description = "新增plan")
    public Plan addPlan(@RequestBody Plan plan){
        return planS.insertPlan(plan);
    }

    //OK
    @CrossOrigin
    @GetMapping(path="/plans",produces = "application/json;charset=UTF-8")
    public List<Plan> findAllPlan(){
        List<Plan> result = planS.getAllPlan();
        return  result;
    }

    //OK
    @DeleteMapping(path = "/plans/delete")
    public String deletePlan(@RequestParam Integer planId){
        planS.deletePlanById(planId);
        return ("刪除成功");
    }

    @Transactional
    @PutMapping("/plans/update")
    public String updatePlan(@RequestParam int planId, @RequestParam("planName") String planName, @RequestParam("planPrice") int planPrice,
                             @RequestParam("planDescription") String planDescription, @RequestParam("planStatus") String planStatus, @RequestParam("planPicture") String planPicture ){
        planS.updatePlanById(planId,planName,planPrice,planDescription,planStatus,planPicture);
        return "更新成功";
    }

}
