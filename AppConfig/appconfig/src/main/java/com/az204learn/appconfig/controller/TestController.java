package com.az204learn.appconfig.controller;

import com.az204learn.appconfig.config.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private MyProperties myProperties;

//    Key should be like this /application/config.message
    @Value("${config.message}")
    private String database;

    @RequestMapping("/check")
    public String check() {
        return database;
    }
}
