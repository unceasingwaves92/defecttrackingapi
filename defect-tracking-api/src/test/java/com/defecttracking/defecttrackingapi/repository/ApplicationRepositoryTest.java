package com.defecttracking.defecttrackingapi.repository;


import com.defecttracking.defecttrackingapi.entity.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    public void testFindAll(){
        saveApplication();
        List<Application> applicationList = applicationRepository.findAll();
        assertEquals(1, applicationList.size());
    }

    @Test
    public void testFindByApplicationName() {
        saveApplication();
        Application application = applicationRepository.findByApplicationName("Digital Banking").get();
        assertEquals("Digital Banking", application.getApplicationName());
    }

    @Test
    public void testFindById() {
        saveApplication();
        Application application = applicationRepository.findById(1l).get();
        assertEquals(1, application.getApplicationId());
    }

    public void saveApplication(){
        Application application = new Application();
        application.setApplicationId(1);
        application.setApplicationName("Digital Banking");
        application.setDescription("Online banking");
        application.setOwner("JPMC");
        applicationRepository.save(application);
    }


}
