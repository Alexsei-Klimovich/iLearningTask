package ru.alexsei.task3.model;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {

    @Id

    @GeneratedValue(strategy = GenerationType.AUTO )
    @Column(name = "message_id")
    Long messageId;

    @Column(name = "is_read",columnDefinition = "bool default false")
    Boolean isRead = false;

    @Column(name = "recipient_username")
    String  recipientUsername;

    @Column(name = "sender_username")
    String senderUsername;

    @Column(name = "topic")
    String topic;

    @Column(name = "text")
    String text;

    @Column(name = "sent_time", columnDefinition = "timestamp default now()")
    Date sentTime;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }
}
