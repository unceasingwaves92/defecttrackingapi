package com.defecttracking.defecttrackingapi.service;

import com.defecttracking.defecttrackingapi.entity.Application;
import com.defecttracking.defecttrackingapi.exception.ApplicationNotFoundException;
import com.defecttracking.defecttrackingapi.model.ApplicationRequest;
import com.defecttracking.defecttrackingapi.model.ApplicationVO;
import com.defecttracking.defecttrackingapi.repository.ApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            applicationVO.setApplicationName(application.getApplicationName());
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
    public ApplicationVO getApplicationId(long id) throws ApplicationNotFoundException {
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
        applicationVO.setApplicationName(application.get().getApplicationName());
        applicationVO.setOwner(application.get().getOwner());
        return applicationVO;
    }

    /**
     * Get application by name in custom JPA
     * @param name
     * @return
     */

    @Override
    public ApplicationVO findByName(String name) throws ApplicationNotFoundException {
        log.info("Input to ApplicationServiceImpl.FindByName, name:{} ", name);
        ApplicationVO applicationVO = null;
        if(name==null){
            log.info("Invalid application name, application nameId:{name}", name);
            throw new ApplicationNotFoundException("invalid application name");
        }
        Optional<Application> application = applicationRepository.findByApplicationName(name);
        if(application.isPresent()){
            log.info("Find application by name response: {}", application.get());
            applicationVO = new ApplicationVO();
            applicationVO.setApplicationId(application.get().getApplicationId());
            applicationVO.setDescription(application.get().getDescription());
            applicationVO.setApplicationName(application.get().getApplicationName());
            applicationVO.setOwner(application.get().getOwner());
        }
        return applicationVO;
    }

    /**
     * Save application details
     * @param applicationRequest
     * @return
     * @throws ApplicationNotFoundException
     */

    public ApplicationVO save(ApplicationRequest applicationRequest) throws ApplicationNotFoundException {
        log.info("Inside the ApplicationServiceImpl.save method and params, applicationRequest:{}", applicationRequest);

        if(applicationRequest == null){
            log.info("Invalid application request");
            throw new ApplicationNotFoundException("Invalid application request");
        }
        // applicationrequest convert into application entity
        Application application = new Application();
        if(applicationRequest.getApplicationId() > 0){
            application.setApplicationId(applicationRequest.getApplicationId());
        }
        if(applicationRequest.getApplicationName() != null){
        application.setApplicationName(applicationRequest.getApplicationName());}

        if(applicationRequest.getDescription() != null){
        application.setDescription(applicationRequest.getDescription());}

        if(applicationRequest.getOwner()!= null){
        application.setOwner(applicationRequest.getOwner());}

        // Save the entity in JPA repository
        Application applicationResponse = applicationRepository.save(application);
        ApplicationVO applicationVO = null;
        if(applicationResponse != null) {
            log.info("Application Response, applicationResponse:{}", applicationResponse);
            // another pojo class it will insert the record
            applicationVO = new ApplicationVO();
            applicationVO.setApplicationId(application.getApplicationId());
            applicationVO.setApplicationName(application.getApplicationName());
            applicationVO.setOwner(application.getOwner());
            applicationVO.setDescription(application.getDescription());
        }
        return applicationVO;
    }

    /**
     * Delete by application ID
     * @param id
     * @return
     * @throws ApplicationNotFoundException
     */

    public String delete(long id) throws ApplicationNotFoundException {
        log.info("Input to AppplicationServiceImpl.delete, id:{}", id);
        if(id<0){
            log.info("Invalid application id");
            throw new ApplicationNotFoundException("Invalid application id");
        }
        try {
            applicationRepository.deleteById(id);
        }catch(Exception ex){
            log.error("Exception while deleting application");
            throw new ApplicationNotFoundException("Exception while deleting application");
        }
        return "Application has been deleted";
        }
    }




