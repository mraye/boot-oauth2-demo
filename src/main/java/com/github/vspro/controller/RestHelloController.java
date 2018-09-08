package com.github.vspro.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 普通的资源
 */

@RestController
public class RestHelloController {


    @RequestMapping("/ping")
    public String ping() throws JsonProcessingException {
        return "pong";
    }



}
