package com.microservices.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class oAuthController {

    @GetMapping("/")
    public String home(){
        return "welcome to home";
    }

    @GetMapping("/secured")
    public String Secured(){
        return "welcome to secure page";
    }
}
