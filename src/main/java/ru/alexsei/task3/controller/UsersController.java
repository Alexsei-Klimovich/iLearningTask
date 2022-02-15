package ru.alexsei.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alexsei.task3.model.User;
import ru.alexsei.task3.service.MyUserDetailsService;

import java.util.List;
import java.util.Map;

@Controller
public class UsersController {

    @Autowired
    MyUserDetailsService userDetailsService;


    @GetMapping("/users")
    String getUsers(Model model){
        List<User> users = userDetailsService.allUsers();
        model.addAttribute("users",users);
        return "usersForm";
    }

    @PostMapping("/users")
    String usersToolbar(@Nullable @RequestParam("checkboxName") String checkboxValue[], @RequestParam(name = "buttonType") String buttonType){

        switch (buttonType){
            case ("delete"):
                if(checkboxValue!=null) {
                    for (String s : checkboxValue) {
                        userDetailsService.deleteUserById(Long.parseLong(s));
                    }
                }
                break;
            case ("unlock"):
                if(checkboxValue!=null) {
                    for (String s : checkboxValue) {
                        userDetailsService.unlockUserById(Long.parseLong(s));
                    }
                }
                break;
            case ("lock"):
                if(checkboxValue!=null) {
                    for (String s : checkboxValue) {
                        userDetailsService.lockUserById(Long.parseLong(s));
                    }
                }
                break;
        }
        return "redirect:/users";
    }








}
