package com.jobboard.jobboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProtectedController {
    @GetMapping("/protected/hello")
    public String hello() {
        return "Hello secure world!";
    }
}
