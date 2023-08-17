package com.team.gallexiv.ctrl.test.startOK;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StartAppOk {

    @GetMapping("/testStart")
    @ResponseBody
    public String testStart() {
        return "good";
    }

}
