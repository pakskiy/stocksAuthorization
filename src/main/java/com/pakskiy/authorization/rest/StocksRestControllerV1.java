package com.pakskiy.authorization.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/private")
@RequiredArgsConstructor
public class StocksRestControllerV1 {

    @GetMapping("/stocks")
    public String stocks() {
        return "Stocks list controller!";
    }

    @GetMapping("/report")
    public String report() {
        return "Stocks report controller!";
    }
}
