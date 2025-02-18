package com.example.demo.Implementation;

import com.example.demo.Classes.Messages;
import com.example.demo.Classes.User;
import com.example.demo.Exception.MessageException;

import java.util.List;

public interface MessageMethods {
    public Messages newMessage(User user, Messages message, Integer chatId);
    public List<Messages> search(User user, Integer chatId, String query);
    public String delete(User user, Integer chatId, Integer messageID)throws MessageException;
    public Messages like(User user,Integer chatId,Integer messageId);
}
