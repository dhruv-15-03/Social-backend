package com.example.demo.Implementation;

import com.example.demo.Classes.Chat;
import com.example.demo.Classes.User;
import com.example.demo.DataBase.ChatAll;
import com.example.demo.DataBase.UserAll;
import com.example.demo.Exception.ChatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMethod implements ChatMethods{
    @Autowired
    private ChatAll chatAll;
    @Autowired
    private UserAll userAll;
    @Override
    public Chat add(Chat chat, User user1, User user2) {
        chat.setTime(LocalDateTime.now());
        List<User> user=chat.getChats();
        chat.setAdmin(user1);
        user.add(user2);
        chat.setChats(user);
        List<Chat> temp1=user1.getChat();
        temp1.add(chat);
        user1.setChat(temp1);
        List<Chat> temp2=user2.getChat();
        temp2.add(chat);
        user2.setChat(temp2);
        userAll.save(user1);
        userAll.save(user2);
        chatAll.save(chat);
        return chat;
    }
    public Chat add(User user1,User user2){
        Chat chat=new Chat();
        chat.setTime(LocalDateTime.now());
        List<Chat> temp1=user1.getChat();
        for(Chat chat1:temp1){
            if(chat1.getAdmin().equals(user2))
                return chat;
            for(User user3: chat.getChats()){
                if(user3.equals(user2))
                    return chat;
            }
        }
        temp1.add(chat);
        user1.setChat(temp1);
        List<Chat> temp2=user2.getChat();
        temp2.add(chat);
        user2.setChat(temp2);
        chat.setAdmin(user1);
        List<User> user=chat.getChats();
        user.add(user2);
        chat.setChats(user);
        userAll.save(user1);
        userAll.save(user2);
        chatAll.save(chat);
        return chat;
    }
    @Override
    public List<Chat> findByUser(User user1, User user2) {
        return chatAll.findChatByUser(user2.getUserName());
    }

    @Override
    public String delete(User user1, User user2) throws ChatException {
        List<Chat> chats=chatAll.findChatByUser(user2.getUserName());
        for(Chat chat:chats){
            if(chat.getChats().size()==2){
                if(chat.getChats().contains(user1)) {
                    List<Chat> temp=user1.getChat();
                    temp.remove(chat);
                    user1.setChat(temp);
                    List<Chat> temp1=user2.getChat();
                    temp1.remove(chat);
                    user2.setChat(temp1);
                    chatAll.delete(chat);
                    return "Chat deleted Successfully";

                }
            }
        }
        throw new ChatException("Can't delete the chat,not a part of it");
    }

    @Override
    public String deleteById(User user, int chatId) throws ChatException{
        Chat chat=chatAll.getReferenceById(chatId);
        if(!user.getChat().contains(chat))
            throw new ChatException("Not Your chat can't delete");
        for(User temp:chat.getChats()){
            List<Chat> c=temp.getChat();
            c.remove(chat);
            temp.setChat(c);
            userAll.save(temp);
        }
        chatAll.delete(chat);
        return "Deleted Successfully";
    }

    @Override
    public Chat changeTheme(User user, User user2, String theme) throws ChatException{
        List<Chat> chats=chatAll.findChatByUser(user2.getUserName());
        for(Chat chat:chats){
            if(chat.getChats().size()==2){
                if(chat.getChats().contains(user)) {
                    chat.setTheme(theme);
                    chatAll.save(chat);
                    userAll.save(user);
                    userAll.save(user2);
                    return chat;
                }
            }
        }
        throw new ChatException("Not your Chat");
    }

    @Override
    public String deleteForMe(User user, int chatId) throws ChatException {
        Chat chat=chatAll.getReferenceById(chatId);
        if(!user.getChat().contains(chat)){
            throw new ChatException("Can't delete the Chat");
        }
        List<Chat> temp=user.getChat();
        temp.remove(chat);
        user.setChat(temp);
        userAll.save(user);
        return "Successfully Deleted the chat for "+user.getName();
    }

    @Override
    public Chat Update(User user, Chat chat) throws ChatException {
        deleteById(user, chat.getChatId());
        return add(chat,user,chat.getChats().get(1));
    }
}
