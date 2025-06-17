package com.chatbot.sqlassistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assistant {
    private List<Map<String, Object>> queryResults;
    private String response;
    private String sqlQuery;
    private String timestamp;
    private String feedback;
}