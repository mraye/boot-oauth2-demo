package com.github.vspro.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/accessDeny")
    public String accessDeny(){
        return "access_deny";
    }

    @RequestMapping("/confirm_access")
    public String confirmAccess(){
        return "access_confirmation";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
