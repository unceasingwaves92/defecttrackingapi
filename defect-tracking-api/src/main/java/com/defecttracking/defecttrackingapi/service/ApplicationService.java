package com.defecttracking.defecttrackingapi.service;

import com.defecttracking.defecttrackingapi.exception.ApplicationNotFoundException;
import com.defecttracking.defecttrackingapi.exceptionhandling.ResourceNotFoundException;
import com.defecttracking.defecttrackingapi.model.ApplicationRequest;
import com.defecttracking.defecttrackingapi.model.ApplicationVO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ApplicationService {
    List<ApplicationVO> findAll();
    CompletableFuture<ApplicationVO> getApplicationId(long id) throws ApplicationNotFoundException, InterruptedException;

    ApplicationVO getAppId(long id) throws ApplicationNotFoundException, InterruptedException;
    CompletableFuture<ApplicationVO> findByName(String name) throws ApplicationNotFoundException, InterruptedException;

    ApplicationVO findGetByName(String name) throws ApplicationNotFoundException, InterruptedException;
    ApplicationVO findByNameApp(String name, long id) throws ApplicationNotFoundException, InterruptedException;
    ApplicationVO save(ApplicationRequest applicationRequest) throws ApplicationNotFoundException;

    String delete(long id) throws ApplicationNotFoundException;

    void clearFindByIdcCache();
    void clearFindByNameCache();


}
