package com.defecttracking.defecttrackingapi.controller;

import com.defecttracking.defecttrackingapi.entity.Application;
import com.defecttracking.defecttrackingapi.exception.ApplicationNotFoundException;
import com.defecttracking.defecttrackingapi.exceptionhandling.ExceptionResponse;
import com.defecttracking.defecttrackingapi.exceptionhandling.ResourceNotFoundException;
import com.defecttracking.defecttrackingapi.model.ApplicationRequest;
import com.defecttracking.defecttrackingapi.model.ApplicationVO;
import com.defecttracking.defecttrackingapi.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    // http://localhost:8085/api/v1/applications/id
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationVO> getApplicationById(@PathVariable("id") long id){
        log.info("Input to ApplicationController.getApplicationById, id:{}", id);
        ApplicationVO applicationVO=null;
        try {
            applicationVO=applicationService.getAppId(id);
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


    // http://localhost:8085/api/v1/applications?name=appName&id=23
    @GetMapping("/name")
    public ResponseEntity<ApplicationVO> getApplicationByName(@RequestParam("name") String appName, @RequestParam("id") int id){
        log.info("Input to ApplicationController.getApplicationByName, appName:{} id:{}", appName, id);
        ApplicationVO applicationVO=null;
        try {
            applicationVO=applicationService.findGetByName(appName);
            if(applicationVO ==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error("Exception while getting applications", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ApplicationVO>(HttpStatus.OK);
    }

    @GetMapping("/appName/id")
    public ResponseEntity<ApplicationVO> getByName(@RequestParam("name") String appName, @RequestParam("id") long id){
        log.info("Input to ApplicationController.getApplicationByName, appName:{} id:{}", appName, id);
        ApplicationVO applicationVO=null;
        try {
            applicationVO=applicationService.findByNameApp(appName, id);
            if(applicationVO ==null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error("Exception while getting applications", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ApplicationVO>(applicationVO, HttpStatus.OK);
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
            log.info("Application details found");
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception ex) {
           if (applicationVO == null) {
               log.info("Application details not found");
               log.info("Exception error while processing the ApplicationController.deleteApplicationById", ex);
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
       }return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/byidandname") //12132ms 6112 ms
    public ResponseEntity<List<ApplicationVO>> getApplicationByIdAndName(@RequestParam("id") long id, @RequestParam("name") String name){
        log.info("Inside the ApplicationController.getApplications");
        CompletableFuture<ApplicationVO> applicationVO = null;
        CompletableFuture<ApplicationVO> applicationVO1 = null;
        List<ApplicationVO> applicationVOS = new ArrayList<>();
        try {
            applicationVO = applicationService.getApplicationId(id);
            log.info("Get application by id response:{}", applicationVO );
            applicationVO1 = applicationService.findByName(name);
            log.info("Get application by name response:{}", applicationVO1);

            CompletableFuture.allOf(applicationVO, applicationVO1).join();

            applicationVOS.add(applicationVO.get());
            applicationVOS.add(applicationVO1.get());
            if(CollectionUtils.isEmpty(applicationVOS)){
                log.info("Application details are not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception ex){
            log.error("Exception while calling getApplications", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<ApplicationVO>>(applicationVOS, HttpStatus.OK);
    }

    @GetMapping("/clearcache")
    public ResponseEntity<String> clearCache(){
        try {
            applicationService.clearFindByIdcCache();
            applicationService.clearFindByNameCache();
        }catch (Exception ex){
            log.error("Exception while calling getApplications", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Cache has been deleted", HttpStatus.OK);
    }


}


