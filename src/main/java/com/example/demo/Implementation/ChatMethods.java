package com.example.demo.Implementation;

import com.example.demo.Classes.Chat;
import com.example.demo.Classes.User;
import com.example.demo.Exception.ChatException;

import java.util.List;

public interface ChatMethods {
    public Chat add(Chat chat, User user1,User user2);
    public List<Chat> findByUser(User user1, User user2);
    public String delete(User user1,User user2) throws ChatException;
    public String deleteById(User user,int chatId)throws ChatException;
    public Chat changeTheme(User user,User user2,String theme)throws ChatException;
    public String deleteForMe(User user,int chatId) throws ChatException;
    public Chat Update(User user,Chat chat)throws ChatException;
}
