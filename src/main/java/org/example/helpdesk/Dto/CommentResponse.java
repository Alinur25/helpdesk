package org.example.helpdesk.Dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private String text;
    private Instant createdAt;
}