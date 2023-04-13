package com.budget.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BudgetController {

    @GetMapping(value = "/helloworld")
    public String sayHelloWorld() {
        return "Hello world";
    }
}
