package com.chatbot.sqlassistant.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.chatbot.sqlassistant.dto.ApiResponse;
import com.chatbot.sqlassistant.dto.Chat;
import com.chatbot.sqlassistant.dto.Request;
import com.chatbot.sqlassistant.dto.Request.Type;
import com.chatbot.sqlassistant.service.AssistantService;

@RestController
@RequestMapping("/api/assistant")
public class AssistantController {
    private final AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @PostMapping("/ask")
    public ResponseEntity<ApiResponse<Map<String, Object>>> askQuestion(
            // Move @Validated to the @RequestBody parameter
            @RequestBody @Validated(Request.AskValidationGroup.class) Request request) {
        
        Map<String, Object> serviceResponse = assistantService.askQuestion(
                request.getQuestion(), request.getSessionId());
        
        return ResponseEntity.ok(ApiResponse.success(serviceResponse));
    }

    @PostMapping("/feedback")
    public ResponseEntity<ApiResponse<String>> provideFeedback(
            @RequestBody @Validated(Request.FeedbackValidationGroup.class) Request request) {
    	
    	request.setType(Type.FEEDBACK);
        
        String message = assistantService.provideFeedback(
                request.getSessionId(), request.getTimestamp(), request.getFeedback());
        
        return ResponseEntity.ok(ApiResponse.success(message));
    }

    @PostMapping("/clear")
    public ResponseEntity<ApiResponse<Map<String, Object>>> clearChat(@RequestParam String sessionId) {
        Map<String, Object> result = assistantService.clearChat(sessionId);
        return ResponseEntity.ok(ApiResponse.success("Chat history cleared", result));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<Chat>>> getChatHistory(@RequestParam String sessionId) {
        List<Chat> history = assistantService.getChatHistory(sessionId);
        return ResponseEntity.ok(ApiResponse.success(history));
    }

    @GetMapping("/testdb")
    public ResponseEntity<ApiResponse<String>> testDatabaseConnection() {
        String status = assistantService.testDatabaseConnection();
        return ResponseEntity.ok(ApiResponse.success(status));
    }
}