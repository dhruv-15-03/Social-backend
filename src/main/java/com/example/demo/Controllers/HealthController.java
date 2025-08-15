package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.DataBase.StoryAll;
import com.example.demo.DataBase.UserAll;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @Autowired
    private StoryAll storyAll;
    
    @Autowired
    private UserAll userAll;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            long storyCount = storyAll.count();
            long userCount = userAll.count();
            
            response.put("status", "UP");
            response.put("timestamp", LocalDateTime.now());
            response.put("stories", storyCount);
            response.put("users", userCount);
            response.put("message", "Service is healthy and active");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "DOWN");
            response.put("timestamp", LocalDateTime.now());
            response.put("error", e.getMessage());
            
            return ResponseEntity.status(503).body(response);
        }
    }

    @GetMapping("/ping")
    public ResponseEntity<Map<String, Object>> ping() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "pong");
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
