package com.defecttracking.defecttrackingapi.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.Column;

@Getter
@Setter
@ToString
public class ApplicationVO {
    private long applicationId;
    private String description;
    private String applicatioName;
    private String owner;

}