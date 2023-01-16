package com.defecttracking.defecttrackingapi.model;

import com.defecttracking.defecttrackingapi.entity.Application;
import com.defecttracking.defecttrackingapi.entity.Release;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@ToString
public class TicketVO {
    private int ticketId;
    private Application application;
    private Release release;
    private String description;
    private String status;
    private String title;
}
