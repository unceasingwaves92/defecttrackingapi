package com.defecttracking.defecttrackingapi.service;

import com.defecttracking.defecttrackingapi.entity.Application;
import com.defecttracking.defecttrackingapi.exception.ApplicationNotFoundException;
import com.defecttracking.defecttrackingapi.exception.ReleaseNotFoundException;
import com.defecttracking.defecttrackingapi.model.ApplicationVO;
import com.defecttracking.defecttrackingapi.repository.ApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Application service implementation
 * Author
 */
@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<ApplicationVO> findAll() {
        log.info("Inside ApplicationServiceImpl.findAll");
        List<Application> applications = applicationRepository.findAll();
        log.info("Find all application response: {}", applications);
        List<ApplicationVO> applicationVOS = applications.parallelStream()
                .map(application -> {
            ApplicationVO applicationVO = new ApplicationVO();
            applicationVO.setApplicationId(application.getApplicationId());
            applicationVO.setApplicatioName(application.getApplicatioName());
            applicationVO.setOwner(application.getOwner());
            applicationVO.setDescription(application.getDescription());
            return applicationVO;
        }).collect(Collectors.toList());
        return applicationVOS;

}

    /** Get application by id
     *
     */
    @Override
    public ApplicationVO getApplicationbyId(long id) throws ApplicationNotFoundException {
        log.info("Inside ApplicationServiceImpl.getApplicationbyId, id:{}", id);
        if(id<=0){
            log.info("Invalid application id, application Id:{id}", id);
            throw new ApplicationNotFoundException("invalid application id");
        }
        Optional<Application> application = applicationRepository.findById(id);
        if(!application.isPresent()){
            log.info("No record found for application id:{}", id);
            throw new ApplicationNotFoundException("No record found for application id");
        }
        ApplicationVO applicationVO = new ApplicationVO();
        applicationVO.setApplicationId(application.get().getApplicationId());
        applicationVO.setDescription(application.get().getDescription());
        applicationVO.setApplicatioName(application.get().getApplicatioName());
        applicationVO.setOwner(application.get().getOwner());
        return applicationVO;
    }

}
