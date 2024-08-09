package com.robert.googlefirestroredemo.Controller;

import com.google.firebase.internal.FirebaseService;
import com.robert.googlefirestroredemo.Entity.User;
import com.robert.googlefirestroredemo.Manager.FirebaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private FirebaseManager firebaseManager;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/processLogin")
    public String processLogin() {
        return "mainPage";
    }

    @PostMapping("/processRegister")
    public String processRegister(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email
    ) {

        firebaseManager.addUser(
                new User(username, firstName, lastName, email, password.hashCode())
        );
        return "register";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
}
