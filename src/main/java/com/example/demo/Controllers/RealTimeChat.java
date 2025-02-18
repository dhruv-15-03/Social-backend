package com.example.demo.Controllers;

import com.example.demo.Classes.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class RealTimeChat {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/chat/{groupId}")
    public Messages sendToUser(@Payload Messages messages, @DestinationVariable String groupId){
        simpMessagingTemplate.convertAndSendToUser(groupId,"/private",messages);
        return messages;
    }
}
