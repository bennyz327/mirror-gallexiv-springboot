package com.team.gallexiv.data.model;

import com.team.gallexiv.common.lang.VueData;
import jakarta.servlet.http.Part;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    final PlanDao planD;
    final PlanForShowDao planForShowD;
    final UserDao userD;
    final StatusDao statusD;

    public PlanService(PlanDao planD, PlanForShowDao planForShowD, UserDao userD, StatusDao statusD) {
        this.planD = planD;
        this.planForShowD = planForShowD;
        this.userD = userD;
        this.statusD = statusD;
    }

    // 取得單筆plan
    public VueData getPlanById(Integer planId) {
        String accountName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Userinfo> thisUser = userD.findByAccount(accountName);
        Optional<Plan> optionalPlan = planD.findById(planId);
        if (thisUser.isPresent() && optionalPlan.isPresent()) {
            return VueData.ok(optionalPlan.orElse(null));
        }
        return VueData.error("查詢失敗");
    }

    //在 user 設定頁面取得 plan
    public VueData getPlanByUserId(String account) {
        String accountName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Userinfo> thisUser = userD.findByAccount(accountName);
        Integer thisUserId = thisUser.get().getUserId();
        if (thisUser.isPresent()) {
            return VueData.ok(planD.findPlanByUserIdAndStatus(thisUserId));
        }
        return VueData.error("查詢失敗");
    }

    // --------先略過此處-------------
    public PlanForShow getPlanForShowById(int planId) {
        Optional<PlanForShow> plan = planForShowD.findById(planId);
        return plan.orElse(null);
    }
    // -----------------------------

    // 取得全部plan
    public VueData getAllPlan() {
        List<Plan> result = planD.findAll();
        if (result.isEmpty()) {
            return VueData.error("查詢失敗");
        }
        return VueData.ok(result);
    }

    // --------先略過此處-------------
    public List<PlanForShow> getAllPlanForShow() {
        return planForShowD.findAll();
    }
    // -----------------------------

    // public List<Plan> getAllPlanByUserId (Integer userId){
    // String accountName = (String)
    // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // planD.getAllPlanByUserId(userId);
    // Optional<Userinfo> thisUser = userinfoD.findByAccount(accountName);
    // if(thisUser.isPresent()){
    //
    // List<Plan> result = planD.getAllPlanByUserId(5);
    // return result;
    // }
    // return null;
    // }

    public List<Plan> getAllPlanByUserId(Integer userId) {
        String accountName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Userinfo> optionalUserinfo = userD.findByAccount(accountName);
        List<Plan> planList = new ArrayList<>();

        planList.add(planD.findById(userId).get());

        return planList;
    }

    // 新增plan
    public VueData insertPlan(Plan plan) {
        // 取得userId
        String accountName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Userinfo> thisUser = userD.findByAccount(accountName);

        int thisPlanStatusId = plan.getPlanStatusByStatusId().getStatusId();
        System.out.println("statusID: " + thisPlanStatusId);
        Optional<Status> status = statusD.findById(thisPlanStatusId);

        if (status.isPresent() && thisUser.isPresent()) {
            System.out.println("有進去");
            // 須加上把圖檔轉成base64，並稍微改一下getStatus邏輯
            plan.setPlanStatusByStatusId(status.get());
            plan.setOwnerIdByUserId(thisUser.get());
            return VueData.ok(planD.save(plan));
        }

        return VueData.error("新增失敗");
    }

    // 刪除plan
    public VueData deletePlanById(Integer planId) {
        try {
            String accoutName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Userinfo> thisUser = userD.findByAccount(accoutName);
            Integer thisUserId = thisUser.get().getUserId();

            Optional<Plan> planOptional = planD.findById(planId);
            Integer thisPlanUserId = planOptional.get().getOwnerIdByUserId().getUserId();

            Integer thisPlanStatusId = 18;
            Optional<Status> thisPlanStatus = statusD.findById(thisPlanStatusId);

            if (planOptional.isPresent() && thisUserId == thisPlanUserId) {
                Plan deletePlan = planOptional.get();
                deletePlan.setPlanStatusByStatusId(thisPlanStatus.get());
                planD.save(deletePlan);

            }
            return VueData.ok("刪除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return VueData.error("刪除失敗");
        }

    }

    // 更新plan
    public VueData updatePlanById(Plan plan) {
        String accountName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Userinfo> thisUser = userD.findByAccount(accountName);
        Optional<Plan> optional = planD.findById(plan.getPlanId());

        if (thisUser.isPresent() && optional.isPresent()) {
            Plan result = optional.get();
            result.setPlanName(plan.getPlanName());
            result.setPlanPrice(plan.getPlanPrice());
            result.setPlanStatusByStatusId(new Status(plan.getPlanStatusByStatusId().getStatusId()));
            result.setPlanDescription(plan.getPlanDescription());
            // 增加轉成base64的功能
            result.setPlanPicture(plan.getPlanPicture());
            return VueData.ok(result);
        }
        return VueData.error("更新失敗");

    }

}
