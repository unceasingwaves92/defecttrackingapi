package com.defecttracking.defecttrackingapi.service;

import com.defecttracking.defecttrackingapi.exception.ReleaseNotFoundException;
import com.defecttracking.defecttrackingapi.exception.TicketNotFoundException;
import com.defecttracking.defecttrackingapi.model.TicketRequest;
import com.defecttracking.defecttrackingapi.model.TicketVO;

import java.util.List;

public interface TicketService {
    List<TicketVO> findAll();
    TicketVO getTicketById(int id) throws ReleaseNotFoundException;

    TicketVO save(TicketRequest ticketRequest) throws TicketNotFoundException;

}
