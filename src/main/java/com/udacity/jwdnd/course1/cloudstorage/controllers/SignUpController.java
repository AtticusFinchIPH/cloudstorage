package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dataForms.UserForm;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public  String getSignUpPage(@ModelAttribute("signupForm")UserForm userForm, Model model) {
        return "signup";
    }

    @PostMapping
    public String createUser(@ModelAttribute("signupForm")UserForm userForm, Model model, RedirectAttributes redirectAttributes) {
        String signupError = null;
        if(userService.isUserExisted(userForm)) {
            signupError = "Existed user name, please choose another one!";
        }
        if(signupError == null) {
            try {
                int userRow = userService.createUser(userForm);
                if (userRow < 0) {
                    signupError = "Something went wrong, please try again!";
                }
            } catch (PersistenceException e) {
                signupError = "Something went wrong, please try again!";
            }
        }
        if(signupError != null) {
            model.addAttribute("signupError", signupError);
            return "signup";
        } else {
            redirectAttributes.addAttribute("signup", true);
            return "redirect:/login";
        }
    }
}
