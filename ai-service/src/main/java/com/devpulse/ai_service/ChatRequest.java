package com.devpulse.ai_service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ChatRequest {

    private String prompt;

    public ChatRequest() {
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    
    @PostMapping("/api/ai/chat")
    public String chat(@RequestBody ChatRequest request) {
        return "You asked: " + request.getPrompt();
    }
    
}