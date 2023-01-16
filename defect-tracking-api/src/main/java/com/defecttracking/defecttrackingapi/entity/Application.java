package com.defecttracking.defecttrackingapi.entity;
import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Data
@Table(name="APPLICATION_TBL1")
public class Application implements Serializable {
    public static final Long serialVersionUID = 342343l;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_gen")
    @SequenceGenerator(name = "application_gen", sequenceName = "APPLICATION_TBL1_SEQ",allocationSize = 1)
    @Column(name = "application_id")
    private long applicationId;

    private String description;

    @Column(name = "application_name")
    private String applicatioName;

    private String owner;

   // @OneToMany(mappedBy = "application")
   // private Set<Ticket> ticket;


}
