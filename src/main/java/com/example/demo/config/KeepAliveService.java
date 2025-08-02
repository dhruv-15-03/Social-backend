package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class KeepAliveService {

    @Autowired
    private RestTemplate restTemplate;
    
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    
    @Value("${server.port:8080}")
    private String serverPort;
    
    @Value("${keep-alive.url:http://localhost}")
    private String baseUrl;

    public void startKeepAlive() {
        // Schedule keep-alive ping every 10 minutes
        scheduler.scheduleAtFixedRate(() -> {
            try {
                pingHealth();
            } catch (Exception e) {
                System.err.println("Keep-alive ping failed: " + e.getMessage());
            }
        }, 1, 10, TimeUnit.MINUTES);

        System.out.println("Keep-alive service started - pinging every 10 minutes");
    }

    private void pingHealth() {
        try {
            String url = baseUrl + ":" + serverPort + "/api/ping";
            restTemplate.getForObject(url, String.class);
            System.out.println("Keep-alive ping successful at " + java.time.LocalDateTime.now());
        } catch (ResourceAccessException e) {
            // Try alternative URL for deployed environment
            try {
                String deployedUrl = baseUrl + "/api/ping";
                restTemplate.getForObject(deployedUrl, String.class);
                System.out.println("Keep-alive ping successful (deployed) at " + java.time.LocalDateTime.now());
            } catch (Exception ex) {
                System.err.println("Keep-alive ping failed on both URLs: " + ex.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Keep-alive ping error: " + e.getMessage());
        }
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
