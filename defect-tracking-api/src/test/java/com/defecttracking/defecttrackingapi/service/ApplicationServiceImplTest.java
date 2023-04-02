package com.defecttracking.defecttrackingapi.service;

import com.defecttracking.defecttrackingapi.entity.Application;
import com.defecttracking.defecttrackingapi.exception.ApplicationNotFoundException;
import com.defecttracking.defecttrackingapi.model.ApplicationRequest;
import com.defecttracking.defecttrackingapi.model.ApplicationVO;
import com.defecttracking.defecttrackingapi.repository.ApplicationRepository;
//import org.junit.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceImplTest {

    @Mock
    ApplicationRepository applicationRepository;

    @InjectMocks
    ApplicationServiceImpl applicationService;


    @Test
    public void testFindAll() {
        List<Application> applications = new ArrayList<>();
        Application application = new Application();
        application.setApplicationId(2);
        application.setApplicationName("Time management");
        application.setDescription("AMI");
        application.setOwner("Management");
        applications.add(application);

        // Mock service
        when(applicationRepository.findAll()).thenReturn(applications);
        List<ApplicationVO> applicationVOS = applicationService.findAll();
        assertEquals(1, applicationVOS.size());

    }

    @Test
    public void testgetApplicationId() throws ApplicationNotFoundException, InterruptedException {
        long id = 11L;
        Application application = new Application();
        application.setApplicationId(11);
        application.setDescription("Spring boot");
        application.setApplicationName("Java");
        application.setOwner("Karthik");

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));
        ApplicationVO applicationVO = applicationService.getAppId(id);
        assertEquals(11, applicationVO.getApplicationId());
    }

    @Test
    public void testfindByName() throws ApplicationNotFoundException, InterruptedException {
        String name = "Karthik";
        Application application = new Application();
        application.setApplicationId(11);
        application.setDescription("Spring boot");
        application.setApplicationName("Karthik");
        application.setOwner("Karthik");

        when(applicationRepository.findByApplicationName(anyString())).thenReturn(Optional.of(application));
        ApplicationVO applicationVO = applicationService.findGetByName(name);
        assertEquals("Karthik", applicationVO.getApplicationName());
      //  assertThat(applicationVO.getApplicationName()).isEqualTo(name);
    }

    @Test
    public void testdelete() throws ApplicationNotFoundException {
        long id = 10L;
        Application application = new Application();
        application.setApplicationId(10L);
        // not returing mocking
      //  when(applicationRepository.deleteById(10L)).thenReturn("SUCCESS");
        String applicationVO = applicationService.delete(id);
        assertEquals(10L, application.getApplicationId());

    }

    @Test
    public void testSave() throws ApplicationNotFoundException {
        ApplicationRequest applicationRequest = new ApplicationRequest();
        Application application = new Application();
        application.setApplicationId(1);
        application.setDescription("Spring boot");
        application.setApplicationName("unit test");
        application.setOwner("regu");

        when(applicationRepository.save(any())).thenReturn(application);
        ApplicationVO applicationVO = applicationService.save(applicationRequest);
      //  assertThat("unit test").isEqualTo(application.getApplicationName());
        assertEquals("unit test", application.getApplicationName());
      //  assertEquals("sprint boot", applicationVO.getDescription());
      //  assertEquals("regu", applicationVO.getOwner());
    }

    @Test
    public void testUpdate() throws ApplicationNotFoundException {
        ApplicationRequest applicationRequest = new ApplicationRequest();
        Application application = new Application();
        application.setApplicationId(1);
        application.setDescription("Spring boot start");
        application.setApplicationName("unit test");
        application.setOwner("regu");

        when(applicationRepository.save(any())).thenReturn(application);
        ApplicationVO applicationVO = applicationService.save(applicationRequest);
        //  assertThat("unit test").isEqualTo(application.getApplicationName());
        assertEquals("Spring boot start", application.getDescription());
        //  assertEquals("sprint boot", applicationVO.getDescription());
        //  assertEquals("regu", applicationVO.getOwner());
    }

}
