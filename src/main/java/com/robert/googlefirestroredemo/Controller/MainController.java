package com.robert.googlefirestroredemo.Controller;

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
    public String processLogin(@RequestParam String username, @RequestParam String password){
        if(!firebaseManager.loginUserCheck(username, password)) {
            return "redirect:/";
        }
        return "redirect:/mainPage";
    }

    @GetMapping("/mainPage")
    public String mainPage() {
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
                new User(username, firstName, lastName, email, (long) password.hashCode())
        );
        return "redirect:/mainPage";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
}
