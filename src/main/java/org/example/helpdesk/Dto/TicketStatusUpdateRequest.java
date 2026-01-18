package org.example.helpdesk.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.helpdesk.Entity.TicketStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketStatusUpdateRequest {
    @NotNull
    private TicketStatus status;
}
