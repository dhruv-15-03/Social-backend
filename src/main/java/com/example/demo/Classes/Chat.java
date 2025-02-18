package com.example.demo.Classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatId;
    @ManyToOne
    private User admin;
    @ManyToMany()
    private List<User> chats=new ArrayList<>();
    private LocalDateTime time;
    @OneToMany
    private List<Messages> message=new ArrayList<>();
    private String theme;

    public Chat() {
    }

    public Chat(Integer chatId, List<User> chats, LocalDateTime time, List<Messages> message, String theme,User admin) {
        this.chatId = chatId;
        this.chats = chats;
        this.time = time;
        this.message = message;
        this.theme = theme;
        this.admin=admin;
    }
}
