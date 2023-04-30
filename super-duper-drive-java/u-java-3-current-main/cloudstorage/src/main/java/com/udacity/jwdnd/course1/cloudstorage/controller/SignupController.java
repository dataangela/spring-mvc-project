package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
@AllArgsConstructor
public class SignupController {
    private final UserService userService;

    @GetMapping
    public String signupPage() {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model) {
        String error = "";

        if (!userService.isUsernameAvailable(user.getUsername())) {
            error = "The username already exists.";
        }

        try{
            userService.createUser(user);
        }catch(Exception e){
            error="Signup exception occurred";
            System.out.println("Signup exception occurred");
        }

        if (error.isEmpty()) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", error);
        }

        return "redirect:/login";

    }
}