package org.example.helpdesk.Repository;


import org.example.helpdesk.Entity.Ticket;
import org.example.helpdesk.Entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(TicketStatus status);
}
