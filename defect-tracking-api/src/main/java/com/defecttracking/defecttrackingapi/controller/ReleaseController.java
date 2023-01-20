package com.defecttracking.defecttrackingapi.controller;

import com.defecttracking.defecttrackingapi.exception.ReleaseNotFoundException;
import com.defecttracking.defecttrackingapi.model.ReleaseRequest;
import com.defecttracking.defecttrackingapi.model.ReleaseVO;
import com.defecttracking.defecttrackingapi.service.ReleaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/releases")
@Slf4j
public class ReleaseController {

    @Autowired
    ReleaseService releaseService;

    @GetMapping
    public ResponseEntity<List<ReleaseVO>> getRelease() {
        log.info("Inside the ReleaseController.getRelease");
        List<ReleaseVO> releaseVOS = null;
        try {
            releaseVOS = releaseService.findAll();
            if(CollectionUtils.isEmpty(releaseVOS)){
                log.info("Application details are not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(Exception ex){
            log.info("Exception while calling getApplications", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<ReleaseVO>>(releaseVOS, HttpStatus.OK);
    }

    //http://localhost:8085/api/vi/releases/id
    @GetMapping("/{id}")
    public ResponseEntity<ReleaseVO> getReleaseById(@PathVariable("id") int id) {
        log.info("Input to ReleaseController.getReleaseById, id:{}", id);
        ReleaseVO releaseVOS = null;
        try {
            releaseVOS = releaseService.getReleasebyId(id);
            log.info("Release details for the release id:{}, and details:{}", id, releaseVOS);
            if(releaseVOS == null){
                log.info("Release details not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.info("Exception error while processing the ReleaseController.getReleasebyId", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ReleaseVO>(releaseVOS, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<String> saveRelease(@RequestBody ReleaseRequest releaseRequest){
        log.info("Inside ReleaseController.save, releaseVO:{}", releaseRequest);
        if(releaseRequest == null){
            log.info("Invalid release request");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ReleaseVO releaseVO = null;
        try {
            releaseVO = releaseService.save(releaseRequest);
            if(releaseVO==null){
            log.error("Release details not saved");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        }catch(Exception ex){
            log.error("Exception while saving releases");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
            return new ResponseEntity<>("Release has been saved", HttpStatus.OK);
    }

}
