package com.defecttracking.defecttrackingapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Getter
@Setter
@ToString
public class ReleaseVO {
    private int releaseId;
    private String description;
    private String releaseDate;
}
