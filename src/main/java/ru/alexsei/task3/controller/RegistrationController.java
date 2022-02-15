package ru.alexsei.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.alexsei.task3.model.User;
import ru.alexsei.task3.service.MyUserDetailsService;

import java.util.Map;

@Controller
public class RegistrationController {

    private BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping("/registration")
    public String saveUser(Model model, @ModelAttribute User user){

        if(userDetailsService.isUserNameExists(user.getUserName()) || userDetailsService.isEmailExists(user.getEmail())){
            model.addAttribute("message", "Username/Email already exists");
            return "registrationPage";
        }
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userDetailsService.createUser(user);
        return "redirect:/login";
    }

}
