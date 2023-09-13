package com.team.gallexiv.data.model;

import com.team.gallexiv.common.lang.VueData;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    final PlanDao planD;
    final PlanForShowDao planForShowD;
    final UserDao userinfoD;
    final StatusDao statusD;

    public PlanService(PlanDao planD, PlanForShowDao planForShowD, UserDao userinfoD, StatusDao statusD) {
        this.planD = planD;
        this.planForShowD = planForShowD;
        this.userinfoD = userinfoD;
        this.statusD = statusD;
    }

    // 取得單筆plan
    public VueData getPlanById(Integer planId) {
        Optional<Plan> optionalPlan = planD.findById(planId);
        if (optionalPlan.isPresent()) {
            return VueData.ok(optionalPlan.orElse(null));
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

    // 新增plan
    public VueData insertPlan(Plan plan, MultipartFile mf)throws IOException {
        String fileName = mf.getOriginalFilename();

        String saveFileDir = "C:/uploadPicture/"+plan.getOwnerIdByUserId().getUserId();
        File saveFilePath = new File(saveFileDir,fileName);

        byte[] b1 = mf.getBytes();
        mf.transferTo(saveFilePath);

        if(fileName !=null && fileName.length()!=0){

        }


        Optional<Userinfo> thisUser = userinfoD.findByUserId(plan.getOwnerIdByUserId().getUserId());
        int thisPlanStatusId = plan.getPlanStatusByStatusId().getStatusId();
        System.out.println("statusID: " + thisPlanStatusId);
        Optional<Status> status = statusD.findById(thisPlanStatusId);

        if (status.isPresent() && thisUser.isPresent()) {
            System.out.println("有進去");
            plan.setPlanStatusByStatusId(status.get());
            plan.setOwnerIdByUserId(thisUser.get());
            return VueData.ok(planD.save(plan));
        }

        return VueData.error("新增失敗");
    }



    // 刪除plan
    public VueData deletePlanById(Integer planId) {
        Optional<Plan> planOptional = planD.findById(planId);
        if (planOptional.isPresent()) {
            planD.deleteById(planId);
            return VueData.ok();
        }
        return VueData.error("刪除失敗");
    }

    // 更新plan
    public VueData updatePlanById(Plan plan) {
        Optional<Plan> optional = planD.findById(plan.getPlanId());

        if (optional.isPresent()) {
            Plan result = optional.get();
            result.setPlanName(plan.getPlanName());
            result.setPlanPrice(plan.getPlanPrice());
            result.setPlanStatusByStatusId(new Status(plan.getPlanStatusByStatusId().getStatusId()));
            result.setPlanDescription(plan.getPlanDescription());
            result.setPlanPicture(plan.getPlanPicture());
            return VueData.ok(result);
        }
        return VueData.error("更新失敗");

    }

}
