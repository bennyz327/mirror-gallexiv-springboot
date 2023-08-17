package com.team.gallexiv.ctrl.test.dispatcherPreSuf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DispatcherConfig {
    @GetMapping("/test/dispatcher")
    @ResponseBody
    public String test() {
//        return "/test/morePath/dispatcherPreSuf";
        return "very good";
    }
}
