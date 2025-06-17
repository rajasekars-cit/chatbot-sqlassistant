package com.chatbot.sqlassistant.service;

import java.util.List;
import java.util.Map;

import com.chatbot.sqlassistant.dto.Chat;

public interface AssistantService {
    Map<String, Object> askQuestion(String question, String sessionId);
    String provideFeedback(String sessionId, String timestamp, String feedback);
    Map<String, Object> clearChat(String sessionId);
    List<Chat> getChatHistory(String sessionId);
    String testDatabaseConnection();
}
