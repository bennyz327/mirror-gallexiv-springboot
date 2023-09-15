package com.team.gallexiv.data.api.Plans;

import com.team.gallexiv.data.model.Plan;
import com.team.gallexiv.data.model.PlanForShow;
import com.team.gallexiv.data.model.PlanService;
import com.team.gallexiv.data.model.UserService;
import com.team.gallexiv.common.lang.VueData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class PlansController {

    final PlanService planS;
    final UserService userS;

    public PlansController(PlanService planS, UserService userS) {
        this.planS = planS;
        this.userS = userS;
    }

    @GetMapping(path = "/plans/{planId}", produces = "application/json")
    @Operation(description = "取得單筆plan (GET BY ID)")
    public VueData getPlanById(@RequestBody Integer planId) {
        return planS.getPlanById(planId);
    }

    // --------先略過此處-------------
    @GetMapping(path = "/plansForShow/{planId}", produces = "application/json")
    @Operation(description = "取得單筆plan (GET BY ID)")
    public PlanForShow getPlanShowById(@PathVariable int planId) {
        return planS.getPlanForShowById(planId);
    }
    // -----------------------------

    @PostMapping(path = "/plans/insert", produces = "application/json;charset=UTF-8")
    @Operation(description = "新增plan")
    public VueData addPlan(@RequestBody Plan plan, @RequestParam("myFile")MultipartFile mf)throws IOException {
        System.out.println("收到" + plan);
        return planS.insertPlan(plan,mf);
    }

    @CrossOrigin
    @GetMapping(path = "/plans", produces = "application/json;charset=UTF-8")
    @Operation(description = "取得全部plan")
    public VueData findAllPlan() {
        return planS.getAllPlan();
    }

    // --------先略過此處-------------
    @CrossOrigin
    @GetMapping(path = "/plansForShow", produces = "application/json;charset=UTF-8")
    public List<PlanForShow> findAllPlanForShow() {
        List<PlanForShow> result = planS.getAllPlanForShow();
        return result;
    }
    // -----------------------------

    @DeleteMapping(path = "/plans/{planId}/delete")
    @Operation(description = "刪除plan(GET BY ID)")
    public VueData deletePlan(@PathVariable Integer planId) {
        return planS.deletePlanById(planId);

    }

    @Transactional // 狀態更新會只有statusId
    @PutMapping("/plans/update")
    @Operation(description = "更新plan")
    public VueData updatePlan(@RequestBody Plan plan) {

        return planS.updatePlanById(plan);

    }

}
