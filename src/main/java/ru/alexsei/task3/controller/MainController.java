package ru.alexsei.task3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping("/")
    public String welcomePage(Model model) {
        return "welcomePage";
    }

    @GetMapping("/users")
    public String usersForm(Model model) {
        return "usersForm";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model){
        return "registrationPage";
    }



}
