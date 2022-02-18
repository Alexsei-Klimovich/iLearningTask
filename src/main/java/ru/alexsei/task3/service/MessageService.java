package ru.alexsei.task3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import ru.alexsei.task3.model.Message;
import ru.alexsei.task3.model.User;
import ru.alexsei.task3.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public Message getMessageById(Long id) {
        return messageRepository.getById(id);
    }

    public void createMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Message> allMessages() {
        return messageRepository.findAll();
    }
}
