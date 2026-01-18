package org.example.helpdesk.Dto;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketUpdateRequest {

    @Size(max = 200)
    private String title;

    private String description;
}