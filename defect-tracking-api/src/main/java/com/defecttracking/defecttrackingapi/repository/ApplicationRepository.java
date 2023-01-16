package com.defecttracking.defecttrackingapi.repository;

import com.defecttracking.defecttrackingapi.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
