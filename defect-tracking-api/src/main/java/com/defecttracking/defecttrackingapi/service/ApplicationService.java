package com.defecttracking.defecttrackingapi.service;

import com.defecttracking.defecttrackingapi.exception.ApplicationNotFoundException;
import com.defecttracking.defecttrackingapi.model.ApplicationRequest;
import com.defecttracking.defecttrackingapi.model.ApplicationVO;

import java.util.List;

public interface ApplicationService {
    List<ApplicationVO> findAll();
    ApplicationVO getApplicationId(long id) throws ApplicationNotFoundException;

    ApplicationVO findByName(String name) throws ApplicationNotFoundException;

    ApplicationVO save(ApplicationRequest applicationRequest) throws ApplicationNotFoundException;

    String delete(long id) throws ApplicationNotFoundException;
}
