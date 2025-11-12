package com.gymapp.repository;

import com.gymapp.model.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

    /**
     * Finds the entire chat history for a user,
     * ordered from oldest to newest.
     */
    List<ChatHistory> findByUser_UserIdOrderByCreatedAtAsc(Long userId);

}