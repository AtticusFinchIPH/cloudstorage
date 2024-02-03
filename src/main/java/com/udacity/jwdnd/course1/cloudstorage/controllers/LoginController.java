package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/login")
public class LoginController {
    private UserService userService;
    public LoginController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String getLoginPage() {
        return "login";
    }
}
