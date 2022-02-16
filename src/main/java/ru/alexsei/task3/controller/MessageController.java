package ru.alexsei.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alexsei.task3.model.Message;
import ru.alexsei.task3.model.User;
import ru.alexsei.task3.repository.MessageRepository;
import ru.alexsei.task3.service.MessageService;
import ru.alexsei.task3.service.MyUserDetailsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MyUserDetailsService userDetailsService;

    @PostMapping("/createMessage")
    String createMessage(Model model, @ModelAttribute Message message, @Nullable @RequestParam("checkboxName") String checkboxValue[]){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(checkboxValue!=null) {
            for (String s : checkboxValue) {
                message.setMessageId(messageRepository.getNextSeriesId());
                User user = userDetailsService.getUserById(Long.parseLong(s));
                message.setRecipientUsername(user.getUserName());
                message.setSenderUsername(authentication.getName());
                message.setSentTime(new Date());
                messageRepository.saveAndFlush(message);
            }
        }
        return  "redirect:/userPage";
    }

    @GetMapping("/createMessage")
    String showCreateMessagePage(Model model, @ModelAttribute Message message){
        List<User> users = userDetailsService.allUsers();
        model.addAttribute("users",users);
        model.addAttribute("errormessage","errormessage");
        model.addAttribute("message", message);
        return "createMessagePage";
    }


}
