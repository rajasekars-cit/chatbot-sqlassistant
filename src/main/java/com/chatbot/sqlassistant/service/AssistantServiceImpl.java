package com.chatbot.sqlassistant.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chatbot.sqlassistant.config.AppConfig;
import com.chatbot.sqlassistant.config.ErrorConstants;
import com.chatbot.sqlassistant.dto.Assistant;
import com.chatbot.sqlassistant.dto.Chat;
import com.chatbot.sqlassistant.dto.User;
import com.chatbot.sqlassistant.exception.AppException;

@Service
public class AssistantServiceImpl implements AssistantService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final ChatClient chatClient;
    private final JdbcTemplate jdbcTemplate;

    public AssistantServiceImpl(ChatClient chatClient, JdbcTemplate jdbcTemplate) {
        this.chatClient = chatClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    private final Map<String, List<Chat>> chatHistory = new HashMap<>();

    public Map<String, Object> askQuestion(String question, String sessionId) {
        sessionId = (null == sessionId || sessionId.isEmpty()) ? UUID.randomUUID().toString() : sessionId;

        List<Chat> sessionHistory = chatHistory.computeIfAbsent(sessionId, k -> new ArrayList<>());

        User userMessage = new User(question, LocalDateTime.now().toString());
        Chat currentChat = new Chat();
        currentChat.setUser(userMessage);

        try {
            if (!isQuestionRelevant(question)) {
                return handleUnknownQuestion(question, sessionId, sessionHistory, currentChat);
            }

            String sqlQuery = generateSqlQuery(question, sessionHistory);
            if (sqlQuery == null || "I don't know".equalsIgnoreCase(sqlQuery.trim())) {
                return handleUnknownQuestion(question, sessionId, sessionHistory, currentChat);
            }

            List<Map<String, Object>> queryResults = jdbcTemplate.queryForList(sqlQuery);
            String naturalLanguageResponse = formatResults(question, queryResults);

            Assistant assistantMessage = new Assistant(
                queryResults,
                naturalLanguageResponse,
                sqlQuery,
                LocalDateTime.now().toString(),
                null // No feedback initially
            );
            currentChat.setAssistant(assistantMessage);

            sessionHistory.add(currentChat);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("response", naturalLanguageResponse);
            responseMap.put("sqlQuery", sqlQuery);
            responseMap.put("queryResults", queryResults);
            responseMap.put("sessionId", sessionId);
            return responseMap;

        } catch (Exception e) {
            log.error("Error in askQuestion for session {}. Error details: {}", sessionId, e.getMessage());
            throw new AppException(ErrorConstants.PROCESSING_ERROR_CODE,
                                   "Error processing request for session " + sessionId + ": " + e.getMessage());
        }
    }

    private boolean isQuestionRelevant(String question) {
        String lower = question.toLowerCase();
        return AppConfig.LIST_OF_TABLES.stream().anyMatch(t -> lower.contains(t.toLowerCase())) ||
               AppConfig.RELEVANT_KEYWORDS.stream().anyMatch(k -> lower.contains(k.toLowerCase()));
    }

    private Map<String, Object> handleUnknownQuestion(String question, String sessionId,
                                                    List<Chat> history, Chat currentTurn) {
        Assistant assistantMessage = new Assistant(
            null, // No query results
            "I don't know",
            null, // No SQL query
            LocalDateTime.now().toString(),
            null
        );
        currentTurn.setAssistant(assistantMessage);
        history.add(currentTurn);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", "I don't know");
        responseMap.put("sessionId", sessionId);
        return responseMap;
    }

    private String generateSqlQuery(String question, List<Chat> history) {
        StringBuilder context = new StringBuilder();
        for (Chat turn : history) {
            if (turn.getUser() != null) {
                context.append("user: ").append(turn.getUser().getQuestion()).append("\n");
            }
            if (turn.getAssistant() != null) {
                if (turn.getAssistant().getResponse() != null) {
                    context.append("assistant: ").append(turn.getAssistant().getResponse()).append("\n");
                }
            }
        }

        Map<String, Object> variables = Map.of(
            "dialect", "postgresql",
            "topK", 15,
            "listOfTables", String.join(", ", AppConfig.LIST_OF_TABLES),
            "schemaDescription", AppConfig.SCHEMA_DESCRIPTION,
            "additionalInstructions", AppConfig.ADDITIONAL_INSTRUCTIONS,
            "chatHistory", context.toString(),
            "question", question
        );

        return chatClient.prompt()
            .system(s -> s.text(AppConfig.SYSTEM_PROMPT_TEMPLATE).params(variables))
            .user(question)
            .call()
            .content();
    }

    private String formatResults(String question, List<Map<String, Object>> results) {
        return chatClient.prompt()
            .system(s -> s.text(AppConfig.RESULT_FORMAT_TEMPLATE)
            .param("question", question)
            .param("results", results.toString()))
            .call()
            .content();
    }

    public String provideFeedback(String sessionId, String timestamp, String feedback) {
        List<Chat> history = chatHistory.get(sessionId);
        if (history == null) {
            throw new AppException(ErrorConstants.NOT_FOUND_CODE, "Session not found for feedback: " + sessionId);
        }

        for (Chat turn : history) {
            if (turn.getAssistant() != null && timestamp.equals(turn.getAssistant().getTimestamp())) {
                turn.getAssistant().setFeedback(feedback);
                return "Feedback received";
            }
        }
        throw new AppException(ErrorConstants.NOT_FOUND_CODE,
            "Assistant message not found for timestamp " + timestamp + " in session " + sessionId + " to provide feedback.");
    }

    public Map<String, Object> clearChat(String sessionId) {
        if (!chatHistory.containsKey(sessionId)) {
            throw new AppException(ErrorConstants.NOT_FOUND_CODE, "Session not found to clear: " + sessionId);
        }
        chatHistory.remove(sessionId);
        return Map.of("message", "Chat history cleared", "sessionId", sessionId);
    }

    public List<Chat> getChatHistory(String sessionId) {
        List<Chat> history = chatHistory.get(sessionId);
        if (history == null) {
            throw new AppException(ErrorConstants.NOT_FOUND_CODE, "Chat history not found for session: " + sessionId);
        }
        return new ArrayList<>(history);
    }

    public String testDatabaseConnection() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return "Database connection successful";
        } catch (Exception e) {
            throw new AppException(ErrorConstants.INTERNAL_SERVER_ERROR_CODE,
                    "Database connection failed: " + e.getMessage(), e);
        }
    }
}