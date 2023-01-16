package com.defecttracking.defecttrackingapi.service;

import com.defecttracking.defecttrackingapi.entity.Release;
import com.defecttracking.defecttrackingapi.entity.Ticket;
import com.defecttracking.defecttrackingapi.exception.ReleaseNotFoundException;
import com.defecttracking.defecttrackingapi.model.ReleaseVO;
import com.defecttracking.defecttrackingapi.model.TicketVO;
import com.defecttracking.defecttrackingapi.repository.ReleaseRepository;
import com.defecttracking.defecttrackingapi.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Ticket service implementation
 * Author
 */

@Service
@Slf4j
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;
    @Override
    public List<TicketVO> findAll() {
        log.info("Inside TicketServiceImpl.findAll");
        List<Ticket> tickets = ticketRepository.findAll();
        log.info("Find all tickets response: {}", tickets);
        List<TicketVO> ticketVOS = tickets.stream().map(ticket -> {
            TicketVO ticketVO = new TicketVO();
            ticketVO.setTicketId(ticket.getTicketId());
            ticketVO.setApplication(ticket.getApplication());
            ticketVO.setRelease(ticket.getRelease());
            ticketVO.setDescription(ticket.getDescription());
            ticketVO.setStatus(ticket.getStatus());
            ticketVO.setTitle(ticket.getTitle());
            return ticketVO;
        }).collect(Collectors.toList());
        return ticketVOS;
    }

    /**
     * Get ticket by id
     * @param id
     * @return
     */
    @Override
    public TicketVO getTicketById(int id) throws ReleaseNotFoundException {
        log.info("Inside TicketServiceImpl.getTicketById, id:{}", id);
        if(id<=0){
            log.info("Invalid ticket id, ticket id:{}", id);
            throw new ReleaseNotFoundException("Invalid ticket id");
        }
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if(!ticket.isPresent()){
            log.info("No record found for the ticket id:{}", id);
            throw new ReleaseNotFoundException("No record found for the ticket id");
        }
        TicketVO ticketVO = new TicketVO();
        ticketVO.setTicketId(ticket.get().getTicketId());
        ticketVO.setApplication(ticket.get().getApplication());
        ticketVO.setRelease(ticket.get().getRelease());
        ticketVO.setDescription(ticket.get().getDescription());
        ticketVO.setStatus(ticket.get().getStatus());
        ticketVO.setTitle(ticket.get().getTitle());
        return ticketVO;
    }
}
