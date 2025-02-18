package com.example.demo.Implementation;

import com.example.demo.Classes.Chat;
import com.example.demo.Classes.Messages;
import com.example.demo.Classes.User;
import com.example.demo.DataBase.ChatAll;
import com.example.demo.DataBase.MessageAll;
import com.example.demo.DataBase.UserAll;
import com.example.demo.Exception.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageImplementation implements MessageMethods{
    @Autowired
    private ChatAll chatAll;
    @Autowired
    private MessageAll messageAll;
    @Autowired
    private UserAll userAll;
    @Autowired
    private ChatMethod method;

    @Override
    public Messages newMessage(User user, Messages message, Integer chatId) {
        message.setTime(LocalDateTime.now());
        Chat chat=chatAll.getReferenceById(chatId);
        List<Messages> temp=chat.getMessage();
        temp.add(message);
        chat.setMessage(temp);
        message.setUser(user);
        messageAll.save(message);
        chatAll.save(chat);
//        method.Update(user,chat);
        return message;
    }

    @Override
    public List<Messages> search(User user, Integer chatId, String query) {
        return messageAll.findByMessage(query);
    }

    @Override
    public String delete(User user, Integer chatId, Integer messageID) throws MessageException {
        Messages messages=messageAll.getReferenceById(messageID);
        Chat chat=chatAll.getReferenceById(chatId);
        List<Chat> c=user.getChat();
        c.remove(chat);
        List<Messages> temp =chat.getMessage();
        temp.remove(messages);
        chat.setMessage(temp);
        c.add(chat);
        user.setChat(c);
        chatAll.save(chat);
        return "Deleted Successfully";
    }

    @Override
    public Messages like(User user, Integer chatId, Integer messageId) {
        Messages messages=messageAll.getReferenceById(messageId);
        Chat chat=chatAll.getReferenceById(chatId);
        List<User> like=messages.getLike();
        like.add(user);
        messages.setLike(like);
        messageAll.save(messages);
        chatAll.save(chat);
        return messages;
    }
}
