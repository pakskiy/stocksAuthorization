package com.pakskiy.authorization.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/private")
@RequiredArgsConstructor
public class StocksController {

    @GetMapping("/list")
    public String list() {
        return "Stocks list controller!";
    }

    @GetMapping("/report")
    public String report() {
        return "Stocks report controller!";
    }
}
