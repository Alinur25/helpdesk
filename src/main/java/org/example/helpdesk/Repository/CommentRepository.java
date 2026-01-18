package org.example.helpdesk.Repository;
import org.example.helpdesk.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTicketIdOrderByIdAsc(Long ticketId);
}