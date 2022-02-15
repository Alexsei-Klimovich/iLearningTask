package ru.alexsei.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.alexsei.task3.model.MyUserDetails;
import ru.alexsei.task3.model.User;
import ru.alexsei.task3.service.MyUserDetailsService;
import ru.alexsei.task3.util.ValidationUtil;

import java.security.Principal;
import java.util.Date;

@Controller
public class MainController {

    @Autowired
    MyUserDetailsService userDetailsService;

    @GetMapping("/")
    public String welcomePage(Model model) {
        return "welcomePage";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model, @ModelAttribute User user){
        model.addAttribute("user", user);
        return "registrationPage";
    }



}
