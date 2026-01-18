package org.example.helpdesk.Entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Enabled
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TicketStatus status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id asc")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();


    @PrePersist
    void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) this.status = TicketStatus.OPEN;
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
