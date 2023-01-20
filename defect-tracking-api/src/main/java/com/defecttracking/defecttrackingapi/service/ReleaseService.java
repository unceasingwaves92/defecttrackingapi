package com.defecttracking.defecttrackingapi.service;

import com.defecttracking.defecttrackingapi.exception.ReleaseNotFoundException;
import com.defecttracking.defecttrackingapi.model.ReleaseRequest;
import com.defecttracking.defecttrackingapi.model.ReleaseVO;

import java.util.List;

public interface ReleaseService {

    List<ReleaseVO> findAll();
    ReleaseVO getReleasebyId(int id) throws ReleaseNotFoundException;

    ReleaseVO save(ReleaseRequest releaseRequest) throws ReleaseNotFoundException;
}
