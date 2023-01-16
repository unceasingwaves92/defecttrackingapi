package com.defecttracking.defecttrackingapi.repository;

import com.defecttracking.defecttrackingapi.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseRepository extends JpaRepository<Release, Integer> {


}
