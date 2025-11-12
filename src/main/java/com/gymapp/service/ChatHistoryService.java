package com.gymapp.service;

import com.gymapp.dto.ChatRequest;
import com.gymapp.dto.ChatResponse;
import com.gymapp.model.ChatHistory;
import com.gymapp.model.Users;
import com.gymapp.model.enums.Sender;
import com.gymapp.repository.ChatHistoryRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;

    // Manual Constructor (as requested)
    public ChatHistoryService(ChatHistoryRepository chatHistoryRepository) {
        this.chatHistoryRepository = chatHistoryRepository;
    }

    /**
     * Gets the full chat history for the *currently logged-in user*.
     */
    @Transactional(readOnly = true)
    public List<ChatResponse> getAllMessagesForCurrentUser() {
        Users currentUser = getAuthenticatedUser();

        return chatHistoryRepository.findByUser_UserIdOrderByCreatedAtAsc(currentUser.getUserId())
                .stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new chat message from the USER, simulates an AI response,
     * and returns the AI's response.
     */
    @Transactional
    public ChatResponse postMessage(ChatRequest request) {
        Users currentUser = getAuthenticatedUser();

        // 1. Save the USER's message
        ChatHistory userMessage = ChatHistory.builder()
                .user(currentUser)
                .sender(Sender.USER)
                .messageText(request.getMessageText())
                .isSummarized(false)
                .build();
        chatHistoryRepository.save(userMessage);

        // 2. Simulate and save the AI's response
        // (This is where you'll call your Python AI service in the future)
        String aiReplyText = "Thanks for sharing! I've made a note of that.";

        ChatHistory aiMessage = ChatHistory.builder()
                .user(currentUser)
                .sender(Sender.AI)
                .messageText(aiReplyText)
                .isSummarized(false)
                .build();
        ChatHistory savedAiMessage = chatHistoryRepository.save(aiMessage);

        // 3. Return only the AI's response
        return mapEntityToResponse(savedAiMessage);
    }

    // --- HELPER METHODS ---

    private Users getAuthenticatedUser() {
        return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private ChatResponse mapEntityToResponse(ChatHistory message) {
        return ChatResponse.builder()
                .messageId(message.getMessageId())
                .userId(message.getUser().getUserId())
                .sender(message.getSender())
                .messageText(message.getMessageText())
                .createdAt(message.getCreatedAt())
                .build();
    }
}