package com.github.vspro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2018/9/8.
 * 受保护的资源
 */

@RestController
@RequestMapping("/api")
public class ResourceController {

    @GetMapping("/apiPing")
    public String apiPing() {
        return "api_pong";
    }
}
