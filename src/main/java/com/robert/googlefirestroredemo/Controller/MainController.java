package com.robert.googlefirestroredemo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/processLogin")
    public String processLogin() {

        return "mainPage";
    }
}
