package com.defecttracking.defecttrackingapi.controller;

import com.defecttracking.defecttrackingapi.model.ApplicationVO;
import com.defecttracking.defecttrackingapi.model.ReleaseVO;
import com.defecttracking.defecttrackingapi.model.TicketVO;
import com.defecttracking.defecttrackingapi.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/tickets")
@Slf4j
public class TicketController {

    @Autowired
    TicketService ticketService;


    @GetMapping
    public ResponseEntity<List<TicketVO>> getTicket() {
        log.info("Inside the TicketController.getTicket");
        List<TicketVO> ticketVOS = null;
        try {
            ticketVOS = ticketService.findAll();
            if(CollectionUtils.isEmpty(ticketVOS)){
                log.info("Application details are not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(Exception ex){
            log.info("Exception while calling getApplications", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<TicketVO>>(ticketVOS, HttpStatus.OK);
    }

    // http://localhost:8085/api/vi/tickets/id
    @GetMapping("/{id}")
    public ResponseEntity<TicketVO> getTicketById(@PathVariable("id") int id){
        log.info("Input to TicketController.getTicketById, id:{}", id);
        TicketVO ticketVO=null;
        try {
            ticketVO=ticketService.getTicketById(id);
            log.info("Ticket details for the ticket id:{}, and details:{}", id, ticketVO);
            if(ticketVO==null){
                log.info("Ticket details not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.info("Exception error while processing the TicketController.getTicketById", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<TicketVO>(ticketVO, HttpStatus.OK);

    }
}
