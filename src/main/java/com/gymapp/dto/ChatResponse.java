package com.gymapp.dto;

import com.gymapp.model.enums.Sender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

    private Long messageId;
    private Long userId;
    private Sender sender; // "USER" or "AI"
    private String messageText;
    private LocalDateTime createdAt;
}