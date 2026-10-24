
package com.devpulse.ai_service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @GetMapping("/api/ai/health")
    public String healthCheck() {
        return "DevPulse AI Service is running!";
    }

    @PostMapping("/api/ai/chat")
    public String chat(@RequestBody ChatRequest request)
            throws Exception {

        ObjectNode ollamaRequest = objectMapper.createObjectNode();
        ollamaRequest.put("model", "qwen2.5:3b");
        ollamaRequest.put("prompt", request.getPrompt());
        ollamaRequest.put("stream", false);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofMinutes(5))
                .POST(HttpRequest.BodyPublishers.ofString(
                        objectMapper.writeValueAsString(ollamaRequest)))
                .build();

        HttpResponse<String> response = httpClient.send(
                httpRequest,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Ollama returned status: " + response.statusCode());
        }

        JsonNode responseJson = objectMapper.readTree(response.body());

        return responseJson.path("response").asText();
    }
}
