package com.gymapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter; // Using our new pattern
import lombok.NoArgsConstructor;
import lombok.Setter; // Using our new pattern
import lombok.ToString; // Using our new pattern
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_summaries")
@ToString(exclude = {"user"})
public class UserSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "summary_id")
    private Long summaryId;

    // This is a One-to-One link back to the user
    // Your SQL (correctly) has this as a UNIQUE column
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users user;

    @Column(name = "summary_text", columnDefinition = "TEXT")
    private String summaryText;

    @UpdateTimestamp // Automatically updates this timestamp on every save
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}