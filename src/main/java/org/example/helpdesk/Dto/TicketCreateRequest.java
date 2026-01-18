package org.example.helpdesk.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketCreateRequest {
    @NotBlank
    @Size(max = 200)
    private String title;

    private String description;
}
