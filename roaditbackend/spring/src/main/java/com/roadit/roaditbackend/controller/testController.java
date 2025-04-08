package com.roadit.roaditbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, Roadit , spring boot application!";
    }
}