package ru.alexsei.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.alexsei.task3.model.User;
import ru.alexsei.task3.service.MyUserDetailsService;

@Controller
public class MainController {
    @Autowired
    MyUserDetailsService userDetailsService;

    @GetMapping("/")
    public String welcomePage(Model model) {
        return "redirect:/userPage";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model, @ModelAttribute User user){
        model.addAttribute("user", user);
        return "registrationPage";
    }
}
