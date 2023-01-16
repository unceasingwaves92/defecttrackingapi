package com.defecttracking.defecttrackingapi.repository;

import com.defecttracking.defecttrackingapi.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
