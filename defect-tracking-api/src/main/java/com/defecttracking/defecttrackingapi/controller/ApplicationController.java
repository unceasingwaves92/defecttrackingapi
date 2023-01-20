package com.defecttracking.defecttrackingapi.controller;

import com.defecttracking.defecttrackingapi.entity.Application;
import com.defecttracking.defecttrackingapi.exception.ApplicationNotFoundException;
import com.defecttracking.defecttrackingapi.model.ApplicationRequest;
import com.defecttracking.defecttrackingapi.model.ApplicationVO;
import com.defecttracking.defecttrackingapi.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RestController
@RequestMapping("api/v1/applications")
@Slf4j
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<ApplicationVO>> getApplication(){
        log.info("Inside the ApplicationController.getApplications");
        List<ApplicationVO> applicationVOS = null;
        try {
            applicationVOS = applicationService.findAll();
            log.info("Application response:{}", applicationVOS);
            if(CollectionUtils.isEmpty(applicationVOS)) {
                log.info("Application details not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(Exception ex){
            log.info("Exception while calling getApplications", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<ApplicationVO>>(applicationVOS, HttpStatus.OK);
    }
    // http://localhost:8085/api/vi/applications/id
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationVO> getApplicationById(@PathVariable("id") long id){
        log.info("Input to ApplicationController.getApplicationById, id:{}", id);
        ApplicationVO applicationVO=null;
        try {
            applicationVO=applicationService.getApplicationId(id);
            log.info("Application details for the application id:{}, and details:{}", id, applicationVO);
            if(applicationVO==null){
                log.info("Application details not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.info("Exception error while processing the ApplicationController.getApplicationById", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ApplicationVO>(applicationVO, HttpStatus.OK);

    }
    // http://localhost:8085/api/vi/applications?name=appName&id=23
    @GetMapping("/name")
    public ResponseEntity<ApplicationVO> getApplicationByName(@RequestParam("name") String appName, @RequestParam("id") int id){
        log.info("Input to ApplicationController.getApplicationByName, appName:{} id:{}", appName, id);
        ApplicationVO applicationVO=null;
        try {
            return new ResponseEntity<ApplicationVO>(applicationService.findByName(appName), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception while getting applications", ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping
    public ResponseEntity<String> saveApplication(@RequestBody ApplicationRequest applicationRequest) {
        log.info("Inside ApplicationController.save, applicationVO:{}", applicationRequest);
        if(applicationRequest==null){
            log.info("Invalid Application Request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ApplicationVO applicationVO = null;
        try {
            applicationVO = applicationService.save(applicationRequest);
            if(applicationVO == null){
                log.info("Application details are not saved");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error("Exception while saving applications");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Application has been saved", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateApplication(@RequestBody ApplicationRequest applicationRequest) {
        log.info("Inside ApplicationController.save, applicationVO:{}", applicationRequest);
        if(applicationRequest==null){
            log.info("Invalid Application Request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ApplicationVO applicationVO = null;
        try {
            applicationVO = applicationService.save(applicationRequest);
            if(applicationVO == null){
                log.info("Application details are not saved");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error("Exception while saving applications");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Application has been saved", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApplicationVO> deleteApplicationById(@PathVariable("id") long id){
        log.info("Input to ApplicationController.deleteApplicationById, id:{}", id);
        String applicationVO=null;
       try {
           applicationService.delete(id);
            log.info("Delete Application details for the application id:{}, and details:{}", id, applicationVO);
            if(applicationVO==null){
                log.info("Application details not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.info("Exception error while processing the ApplicationController.deleteApplicationById", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
