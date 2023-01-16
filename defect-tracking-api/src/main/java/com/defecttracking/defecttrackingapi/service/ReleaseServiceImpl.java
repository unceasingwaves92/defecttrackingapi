package com.defecttracking.defecttrackingapi.service;

import com.defecttracking.defecttrackingapi.entity.Release;
import com.defecttracking.defecttrackingapi.exception.ReleaseNotFoundException;
import com.defecttracking.defecttrackingapi.model.ReleaseVO;
import com.defecttracking.defecttrackingapi.repository.ReleaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Release service implementation
 * Author
 */

@Service
@Slf4j
public class ReleaseServiceImpl implements ReleaseService{

    @Autowired
    private ReleaseRepository releaseRepository;

    @Override
    public List<ReleaseVO> findAll() {
        log.info("Inside ReleaseServiceImpl.findAll");
        List<Release> releases = releaseRepository.findAll();
        log.info("Find all release response: {}", releases);
        List<ReleaseVO> releaseVOS = releases.stream().map(release -> {
            ReleaseVO releaseVO = new ReleaseVO();
            releaseVO.setReleaseId(release.getReleaseId());
            releaseVO.setDescription(release.getDescription());
            releaseVO.setReleaseDate(release.getReleaseDate());
            return releaseVO;
        }).collect(Collectors.toList());
        return releaseVOS;
    }

    /**
     * Get release by id
     * @param id
     * @return
     * @throws ReleaseNotFoundException
     */

    @Override
    public ReleaseVO getReleasebyId(int id) throws ReleaseNotFoundException {
        log.info("Inside ReleaseServiceImpl.getReleasebyId, id:{}", id);
        if(id<=0){
            log.info("Invalid release id, release id:{}", id);
            throw new ReleaseNotFoundException("Invalid release id");
        }
        Optional<Release> release = releaseRepository.findById(id);
        if(!release.isPresent()){
            log.info("No record found for the release id:{}", id);
            throw new ReleaseNotFoundException("No record found for the release id");
        }
        ReleaseVO releaseVO = new ReleaseVO();
        releaseVO.setReleaseId(release.get().getReleaseId());
        releaseVO.setDescription(release.get().getDescription());
        releaseVO.setReleaseDate(release.get().getReleaseDate());
        return releaseVO;
    }
}
