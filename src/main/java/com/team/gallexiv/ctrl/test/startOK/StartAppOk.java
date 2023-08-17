package com.team.gallexiv.ctrl.test.startOK;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartAppOk {

    @GetMapping("/testStart")
    public String testStart() {
        return "/test/testStart";
    }

}
