package org.example.helpdesk.Dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateRequest {

    @NotBlank
    private String text;
}
