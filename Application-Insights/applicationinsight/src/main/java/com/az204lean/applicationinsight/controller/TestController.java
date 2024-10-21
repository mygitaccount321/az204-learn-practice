package com.az204lean.applicationinsight.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/check")
    public String check() {
        return "Application insight";
    }
}
