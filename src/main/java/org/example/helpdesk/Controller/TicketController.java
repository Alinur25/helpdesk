package org.example.helpdesk.Controller;

import jakarta.validation.Valid;
import org.example.helpdesk.Dto.*;
import org.example.helpdesk.Entity.Comment;
import org.example.helpdesk.Entity.Ticket;
import org.example.helpdesk.Entity.TicketStatus;
import org.example.helpdesk.Exception.NotFoundException;
import org.example.helpdesk.Repository.CommentRepository;
import org.example.helpdesk.Repository.TicketRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;

    public TicketController(TicketRepository ticketRepository, CommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping
    public TicketResponse create(@Valid @RequestBody TicketCreateRequest req) {
        Ticket t = Ticket.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .status(TicketStatus.OPEN)
                .build();

        Ticket saved = ticketRepository.save(t);
        return toResponse(saved, List.of());
    }

    @GetMapping
    public List<TicketResponse> list(@RequestParam(required = false) TicketStatus status) {
        List<Ticket> tickets = (status == null)
                ? ticketRepository.findAll()
                : ticketRepository.findByStatus(status);

        return tickets.stream()
                .map(t -> {
                    List<CommentResponse> comments = commentRepository.findByTicketIdOrderByIdAsc(t.getId())
                            .stream().map(this::toCommentResponse).toList();
                    return toResponse(t, comments);
                })
                .toList();
    }

    @GetMapping("/{id}")
    public TicketResponse getById(@PathVariable Long id) {
        Ticket t = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found: id=" + id));

        List<CommentResponse> comments = commentRepository.findByTicketIdOrderByIdAsc(id)
                .stream().map(this::toCommentResponse).toList();

        return toResponse(t, comments);
    }

    @PatchMapping("/{id}/status")
    public TicketResponse updateStatus(@PathVariable Long id,
                                       @Valid @RequestBody TicketStatusUpdateRequest req) {
        Ticket t = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found: id=" + id));

        t.setStatus(req.getStatus());
        Ticket saved = ticketRepository.save(t);

        List<CommentResponse> comments = commentRepository.findByTicketIdOrderByIdAsc(id)
                .stream().map(this::toCommentResponse).toList();

        return toResponse(saved, comments);
    }

    @PostMapping("/{id}/comments")
    public CommentResponse addComment(@PathVariable Long id,
                                      @Valid @RequestBody CommentCreateRequest req) {
        Ticket t = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found: id=" + id));

        Comment c = Comment.builder()
                .ticket(t)
                .text(req.getText())
                .build();

        Comment saved = commentRepository.save(c);
        return toCommentResponse(saved);
    }

    @PatchMapping("/{id}")
    public TicketResponse update(@PathVariable Long id,
                                 @Valid @RequestBody TicketUpdateRequest req) {
        Ticket t = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found: id=" + id));

        if (req.getTitle() != null) t.setTitle(req.getTitle());
        if (req.getDescription() != null) t.setDescription(req.getDescription());

        Ticket saved = ticketRepository.save(t);

        List<CommentResponse> comments = commentRepository.findByTicketIdOrderByIdAsc(id)
                .stream().map(this::toCommentResponse).toList();

        return toResponse(saved, comments);
    }




    private TicketResponse toResponse(Ticket t, List<CommentResponse> comments) {
        return TicketResponse.builder()
                .id(t.getId())
                .title(t.getTitle())
                .description(t.getDescription())
                .status(t.getStatus())
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .comments(comments)
                .build();
    }

    private CommentResponse toCommentResponse(Comment c) {
        return CommentResponse.builder()
                .id(c.getId())
                .text(c.getText())
                .createdAt(c.getCreatedAt())
                .build();
    }
}