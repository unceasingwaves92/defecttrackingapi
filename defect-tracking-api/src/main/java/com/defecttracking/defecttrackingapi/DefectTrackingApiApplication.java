package com.defecttracking.defecttrackingapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DefectTrackingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DefectTrackingApiApplication.class, args);
		log.debug("Application has been started");
	}

}
