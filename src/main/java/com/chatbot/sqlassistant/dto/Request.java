package com.chatbot.sqlassistant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request {

    public enum Type {
        ASK,
        FEEDBACK
    }

    public interface AskValidationGroup {}
    public interface FeedbackValidationGroup {}

    @NotBlank(message = "Session Id cannot be empty for FEEDBACK request", groups = FeedbackValidationGroup.class)
    private String sessionId;

    private Type type = Type.ASK;

    @NotBlank(message = "Question cannot be empty for ASK request", groups = AskValidationGroup.class)
    private String question;

    @NotBlank(message = "Timestamp cannot be empty for FEEDBACK request", groups = FeedbackValidationGroup.class)
    private String timestamp;

    @NotBlank(message = "Feedback cannot be empty for FEEDBACK request", groups = FeedbackValidationGroup.class)
    private String feedback;
}
