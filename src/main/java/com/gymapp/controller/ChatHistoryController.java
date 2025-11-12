package com.gymapp.controller;

import com.gymapp.dto.ChatRequest;
import com.gymapp.dto.ChatResponse;
import com.gymapp.service.ChatHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat") // Base URL for all chat endpoints
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;

    // Manual Constructor (as requested)
    public ChatHistoryController(ChatHistoryService chatHistoryService) {
        this.chatHistoryService = chatHistoryService;
    }

    /**
     * GET /api/v1/chat
     * Gets the full chat history for the authenticated user.
     */
    @GetMapping
    public ResponseEntity<List<ChatResponse>> getChatHistory() {
        List<ChatResponse> history = chatHistoryService.getAllMessagesForCurrentUser();
        return ResponseEntity.ok(history);
    }

    /**
     * POST /api/v1/chat
     * Posts a new message from the user and returns a simulated AI response.
     */
    @PostMapping
    public ResponseEntity<ChatResponse> postMessage(
            @RequestBody ChatRequest request
    ) {
        // The service handles saving both the USER and AI message,
        // but we only return the AI's response
        ChatResponse aiResponse = chatHistoryService.postMessage(request);
        return ResponseEntity.ok(aiResponse);
    }
}