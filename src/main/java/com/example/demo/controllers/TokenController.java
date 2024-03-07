package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/token")
public class TokenController {
    @GetMapping("")
    public String index(@RequestHeader("Authorization") String authorization) {
        // Lấy AT từ header
        return authorization.substring("Bearer ".length());
    }
}
