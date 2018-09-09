package com.github.vspro.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2018/9/8.
 * 受保护的资源
 */


@Api(value = "/api", description = "测试接口")
@RestController
@RequestMapping("/api")
public class ResourceController {


    @GetMapping("/apiPing")
    @ApiOperation(value = "测试连接", httpMethod = "GET")
    public String apiPing() {
        return "api_pong";
    }
}

