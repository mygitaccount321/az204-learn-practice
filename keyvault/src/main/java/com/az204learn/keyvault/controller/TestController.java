package com.az204learn.keyvault.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    //https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-boot-starter-java-app-with-azure-key-vault#use-spring-key-vault-propertysource
    @Value("${databasename}")
    private String database;

    @RequestMapping("/check")
    public String check() {
        return database;
    }
}
