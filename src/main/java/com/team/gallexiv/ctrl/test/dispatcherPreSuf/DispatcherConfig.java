package com.team.gallexiv.ctrl.test.dispatcherPreSuf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DispatcherConfig {
    @GetMapping("/test/dispatcher")
    public String test() {
        return "/test/morePath/dispatcherPreSuf";
    }
}
