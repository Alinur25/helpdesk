package org.example.helpdesk.Dto;
import lombok.*;
import org.example.helpdesk.Entity.TicketStatus;
import java.util.List;

import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    private List<CommentResponse> comments;
}
