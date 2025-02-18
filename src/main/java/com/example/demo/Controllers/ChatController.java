package com.example.demo.Controllers;

import com.example.demo.Classes.Chat;
import com.example.demo.Classes.User;
import com.example.demo.DataBase.ChatAll;
import com.example.demo.DataBase.UserAll;
import com.example.demo.Exception.ChatException;
import com.example.demo.Implementation.ChatMethod;
import com.example.demo.Implementation.UserImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private UserAll userAll;
    @Autowired
    private ChatAll chatAll;
    @Autowired
    private UserImplementation userImplementation;
    @Autowired
    private ChatMethod chatMethod;
    @PostMapping("/new/{userId}")
    public Chat add(@RequestHeader ("Authorization") String jwt,@PathVariable Integer userId){
        User us2=userAll.getReferenceById(userId);
        User user=userImplementation.getFromJwt(jwt);
        return chatMethod.add(user,us2);
    }
    @PostMapping("/newChat/{userId}")
    public Chat addSpecific(@RequestHeader ("Authorization") String jwt,@RequestBody Chat chat,@PathVariable Integer userId){
        User us2=userAll.getReferenceById(userId);
        User user=userImplementation.getFromJwt(jwt);
        return chatMethod.add(chat,user,us2);
    }
    @GetMapping("/findAll")
    public List<Chat> find(@RequestHeader ("Authorization") String jwt)throws ChatException{
        User user=userImplementation.getFromJwt(jwt);
        return chatMethod.findByUser(user,userAll.getReferenceById(user.getId()));
    }
    @DeleteMapping("/deleteForAll/{userId}")
    public String delete(@RequestHeader ("Authorization") String jwt, @PathVariable Integer userId) throws ChatException {
        User user=userImplementation.getFromJwt(jwt);
        return chatMethod.delete(user,userAll.getReferenceById(userId));
    }
    @DeleteMapping("/deleteByIdForAll/{chatId}")
    public String deleteById(@RequestHeader ("Authorization") String jwt, @PathVariable Integer chatId) throws ChatException {
        User user=userImplementation.getFromJwt(jwt);
        return chatMethod.deleteById(user,chatId);
    }
    @PutMapping("/theme/{userId}")
    public Chat theme(@RequestHeader ("Authorization") String jwt, @PathVariable Integer userId, @RequestBody String theme) throws ChatException {
        User user=userImplementation.getFromJwt(jwt);
        return chatMethod.changeTheme(user,userAll.getReferenceById(userId),theme);
    }
    @PutMapping("/update")
    public Chat Update(@RequestHeader ("Authorization") String jwt, @RequestBody Chat chat) throws ChatException {
        User user=userImplementation.getFromJwt(jwt);
        return chatMethod.Update(user,chat);
    }
    @DeleteMapping("/deleteForMe/{chatId}")
    public String deleteForMe(@RequestHeader ("Authorization") String jwt,@PathVariable Integer chatId) throws ChatException {
        User user=userImplementation.getFromJwt(jwt);
        return chatMethod.deleteForMe(user,chatId);
    }
    @GetMapping("/chatAll")
    public List<Chat> all(@RequestHeader ("Authorization") String jwt){
        User user=userImplementation.getFromJwt(jwt);
        return chatAll.findAll();
    }

}
