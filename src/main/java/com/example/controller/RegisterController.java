package com.example.controller;

import com.example.service.UsersRepoService;
import com.example.usersData.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    private UsersRepoService usersRepoService;
    @GetMapping("/register")
    public String addNewUser (User user, Map<String, Object> model){
        try {
            System.out.println("entered");
            usersRepoService.addNewUser(user);
        } catch (NullPointerException e){
            model.put("message", "Choose another name");
            return "/register";
        }

        return "redirect:/login";
    }
}
