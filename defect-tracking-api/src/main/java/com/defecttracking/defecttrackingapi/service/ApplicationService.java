package com.defecttracking.defecttrackingapi.service;

import com.defecttracking.defecttrackingapi.exception.ApplicationNotFoundException;
import com.defecttracking.defecttrackingapi.exception.ReleaseNotFoundException;
import com.defecttracking.defecttrackingapi.model.ApplicationVO;
import com.defecttracking.defecttrackingapi.model.ReleaseVO;

import java.util.List;

public interface ApplicationService {
    List<ApplicationVO> findAll();
    ApplicationVO getApplicationbyId(long id) throws ApplicationNotFoundException;
}
