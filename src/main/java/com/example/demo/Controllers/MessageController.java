package com.example.demo.Controllers;

import com.example.demo.Classes.Messages;
import com.example.demo.Classes.User;
import com.example.demo.DataBase.ChatAll;
import com.example.demo.DataBase.MessageAll;
import com.example.demo.DataBase.UserAll;
import com.example.demo.Exception.MessageException;
import com.example.demo.Implementation.MessageImplementation;
import com.example.demo.Implementation.UserImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private UserAll userAll;
    @Autowired
    private MessageAll messageAll;
    @Autowired
    private UserImplementation userImplementation;
    @Autowired
    private MessageImplementation messageImplementation;
    @Autowired
    private ChatAll chatAll;


    @PostMapping("/new/{chatId}")
    public Messages add(@RequestHeader("Authorization") String jwt, @RequestBody Messages messages, @PathVariable Integer chatId){
        User user=userImplementation.getFromJwt(jwt);
        return messageImplementation.newMessage(user,messages,chatId);
    }
    @GetMapping("/search/{chatId}/{query}")
    public List<Messages> search(@RequestHeader("Authorization") String jwt,@PathVariable Integer chatId,@PathVariable String query){
        User user=userImplementation.getFromJwt(jwt);
        return messageImplementation.search(user,chatId,query);
    }
    @DeleteMapping("/delete/{chatId},{messageId}")
    public String delete(@RequestHeader("Authorization") String jwt,@PathVariable Integer chatId,@PathVariable Integer messageId) throws MessageException {
        User user=userImplementation.getFromJwt(jwt);
        return messageImplementation.delete(user,chatId,messageId);
    }
    @GetMapping("/like/{chatId},{messageId}")
    public Messages like(@RequestHeader("Authorization") String jwt,@PathVariable Integer chatId,@PathVariable Integer messageId){
        User user=userImplementation.getFromJwt(jwt);
        return messageImplementation.like(user,chatId,messageId);
    }
    @GetMapping("/messageALl")
    public List<Messages> all(@RequestHeader("Authorization") String jwt){
        User user=userImplementation.getFromJwt(jwt);
        return messageAll.findAll();
    }
}
