package ru.alexsei.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alexsei.task3.model.Message;
import ru.alexsei.task3.model.User;
import ru.alexsei.task3.repository.MessageRepository;
import ru.alexsei.task3.service.MessageService;
import ru.alexsei.task3.service.MyUserDetailsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MyUserDetailsService userDetailsService;

    @GetMapping("/topic/reply/{id}")
    String getReplyMessagePage(Model model, @PathVariable Long id, @ModelAttribute Message message) {
        message.setText("");
        message.setMessageId(id);
        model.addAttribute("message", message);
        return "replyMessagePage";
    }

    @PostMapping("/topic/reply/{id}")
    String sentMessageReply(Model model, @PathVariable Long id, @ModelAttribute Message message) {
        Message newMessage = messageService.getMessageById(id);
        String newSender = newMessage.getRecipientUsername();
        String newRecipient = newMessage.getSenderUsername();
        String replyTopic = "RE:" + newMessage.getTopic();
        newMessage.setTopic(replyTopic);
        newMessage.setSentTime(new Date());
        newMessage.setText(message.getText());
        newMessage.setRead(false);
        newMessage.setRecipientUsername(newRecipient);
        newMessage.setSenderUsername(newSender);
        messageService.createMessage(newMessage);
        return "redirect:/userPage";
    }

    @GetMapping("/myMessages")
    String showMyMessagesPage(Model model, @ModelAttribute Message message) {
        List<Message> messages = messageService.allMessages();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Message> sortedMessages = new ArrayList<>();
        for (Message m : messages) {
            if (m.getRecipientUsername().equals(authentication.getName())) {
                sortedMessages.add(0, m);
            }
        }
        model.addAttribute("messages", sortedMessages);
        return "myMessagesPage";
    }

    @PostMapping("/myMessages")
    String updateMyMessagePage(Model model, @Nullable @ModelAttribute Message message) {
        return "redirect:/myMessages";
    }

    @GetMapping("/topic/{id}")
    public String showTopicPage(@PathVariable Long id, Model model) {
        Message message = messageService.getMessageById(id);
        model.addAttribute(message);
        messageRepository.readMessage(id);
        return "topicPage";
    }

    @PostMapping("/createMessage")
    String createMessage(Model model, @ModelAttribute Message message, @Nullable @RequestParam("checkboxName") String checkboxValue[]) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (checkboxValue != null) {
            for (String s : checkboxValue) {
                message.setMessageId(messageRepository.getNextSeriesId());
                User user = userDetailsService.getUserById(Long.parseLong(s));
                message.setRecipientUsername(user.getUserName());
                message.setSenderUsername(authentication.getName());
                message.setSentTime(new Date());
                messageRepository.saveAndFlush(message);
            }
        }
        return "redirect:/userPage";
    }

    @GetMapping("/createMessage")
    String showCreateMessagePage(Model model, @ModelAttribute Message message) {
        List<User> users = userDetailsService.allUsers();
        model.addAttribute("users", users);
        model.addAttribute("errormessage", "errormessage");
        model.addAttribute("message", message);
        return "createMessagePage";
    }
}
