package com.team.gallexiv.data.api.Plans;

import com.team.gallexiv.data.model.Plan;
import com.team.gallexiv.data.model.PlanForShow;
import com.team.gallexiv.data.model.PlanService;
import com.team.gallexiv.data.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlansController {

    final PlanService planS;
    final UserService userS;
    public PlansController(PlanService planS, UserService userS) {
        this.planS = planS;
        this.userS = userS;
    }

    @GetMapping(path = "/plansById", produces = "application/json")
    @Operation(description = "取得單筆plan (GET BY ID)")
    public Plan getPlanById(@RequestBody Plan plan) {
        return planS.getPlanById(plan);
    }

    @GetMapping(path = "/plansForShow/{planId}", produces = "application/json")
    @Operation(description = "取得單筆plan (GET BY ID)")
    public PlanForShow getPlanShowById(@PathVariable int planId) {
        return planS.getPlanForShowById(planId);
    }

    @PostMapping(path = "/plans/insert",produces = "application/json;charset=UTF-8")
    @Operation(description = "新增plan")
    public Plan addPlan(@RequestBody Plan plan){
        System.out.println("收到"+plan);
        return planS.insertPlan(plan);
    }

    @CrossOrigin
    @GetMapping(path="/plans",produces = "application/json;charset=UTF-8")
    @Operation(description = "取得全部plan")
    public List<Plan> findAllPlan(){
        return planS.getAllPlan();
    }

    @CrossOrigin
    @GetMapping(path="/plansForShow",produces = "application/json;charset=UTF-8")
    public List<PlanForShow> findAllPlanForShow(){
        return planS.getAllPlanForShow();
    }

    @DeleteMapping(path = "/plans/delete")
    @Operation(description = "刪除plan(GET BY ID)")
    public String deletePlan(@RequestBody Plan plan) {

        return planS.deletePlanById(plan);

    }

    @Transactional //狀態更新會只有statusId
    @PutMapping("/plans/update")
    @Operation(description = "更新plan")
    public Plan updatePlan(@RequestBody Plan plan){

        return planS.updatePlanById(plan);

    }

}
