package com.team.gallexiv.data.api.Plans;

import com.team.gallexiv.data.model.*;
import com.team.gallexiv.common.lang.VueData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    public VueData getOnePlan(@PathVariable Integer planId) {
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
    public VueData addPlan(@RequestBody Plan plan) {

        System.out.println("收到" + plan);
        return planS.insertPlan(plan);
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

    @Transactional
    @DeleteMapping(path = "/plans/{planId}/delete")
    @Operation(description = "刪除plan(GET BY ID)")
    public VueData deletePlan(@PathVariable Integer planId) {
        return planS.deletePlanById(planId);
    }

    @Transactional // 狀態更新會只有statusId
    @PutMapping("/plans/update")
    @Operation(description = "更新plan")
    public VueData updatePlan(@RequestBody Plan plan) {
        System.out.println(plan);

        return planS.updatePlanById(plan);

    }

    // 在 user 設定頁面取得 plan
    @GetMapping(path = "plans/personalPlan")
    public VueData getAllPlanByUserId(@RequestParam(required = false, defaultValue = "1") Integer userId,
            @RequestParam(required = false, defaultValue = "2") Integer state) {
        if (state == 2) {
            return planS.getPlanByUserIdNotOwner(userId);
        }
        if (state == 1) {
            return planS.getPlanByUserId();
        }
        return VueData.error("查詢失敗");
    }

    // @CrossOrigin
    // @PostMapping(path = "plans/notOwner")
    // public VueData getAllPlanNotOwner(@RequestParam Integer userId) {
    // return planS.getPlanByUserIdNotOwner(userId);
    // }

}
