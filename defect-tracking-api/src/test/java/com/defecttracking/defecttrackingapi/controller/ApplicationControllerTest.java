package com.defecttracking.defecttrackingapi.controller;

import com.defecttracking.defecttrackingapi.exception.ApplicationNotFoundException;
import com.defecttracking.defecttrackingapi.model.ApplicationVO;
import com.defecttracking.defecttrackingapi.service.ApplicationService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @Autowired
    MockMvc mockMVC;

    @MockBean
    ApplicationService applicationService;

    // Positive 200 response test cases
    @Test
    public void testGetApplication() throws Exception {
        List<ApplicationVO> applicationVOS = new ArrayList<>();
        ApplicationVO applicationVO = new ApplicationVO();
        applicationVO.setApplicationId(11);
        applicationVO.setApplicationName("L&T");
        applicationVO.setDescription("IT company");
        applicationVO.setOwner("Jeff");
        applicationVOS.add(applicationVO);
        when(applicationService.findAll()).thenReturn(applicationVOS);
        // create the request hit the end point
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications")
                .contentType(MediaType.APPLICATION_JSON);
        // pass the request and return the request
        MvcResult mvcResult = mockMVC.perform(requestBuilder).andReturn();
        // get the response of body
        MockHttpServletResponse response = mvcResult.getResponse();
        // We get the status code of body and verify the expected and actual result
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    // Positive 200 response test cases
    @Test
    public void testGetApplication4() throws Exception {
        List<ApplicationVO> applicationVOS = new ArrayList<>();
        ApplicationVO applicationVO = new ApplicationVO();
        applicationVO.setApplicationId(11);
        applicationVO.setApplicationName("L&T");
        applicationVO.setDescription("IT company");
        applicationVO.setOwner("Jeff");
        applicationVOS.add(applicationVO);

        when(applicationService.findAll()).thenReturn(applicationVOS);
        // create the request hit the end point
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications")
                .contentType(MediaType.APPLICATION_JSON);
        // We passing the request and returns expected json path, if response size is 1 means test is passed or otherwise failed
        mockMVC.perform(requestBuilder).andExpect(jsonPath("$", Matchers.hasSize(1))).andDo(print());
    }

    // Positive 200 response test cases
    @Test
    public void testGetApplication5() throws Exception {
        List<ApplicationVO> applicationVOS = new ArrayList<>();
        ApplicationVO applicationVO = new ApplicationVO();
        applicationVO.setApplicationId(11);
        applicationVO.setApplicationName("L&T");
        applicationVO.setDescription("IT company");
        applicationVO.setOwner("Jeff");
        applicationVOS.add(applicationVO);

        when(applicationService.findAll()).thenReturn(applicationVOS);
        // create the request hit the end point
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications")
                .contentType(MediaType.APPLICATION_JSON);
        mockMVC.perform(requestBuilder).andExpect(status().isOk());
    }



    // 500 Internal server error test cases
    @Test
    public void testGetApplication1() throws Exception {
        List<ApplicationVO> applicationVOS = new ArrayList<>();
        when(applicationService.findAll()).thenThrow(new NullPointerException());
        // create the request hit the end point
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications")
                .contentType(MediaType.APPLICATION_JSON);
        // pass the request and return the request
        MvcResult mvcResult = mockMVC.perform(requestBuilder).andReturn();
        // get the response of body
        MockHttpServletResponse response = mvcResult.getResponse();
        // We get the status code of body and verify the expected and actual result
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    // 404 Not found test cases

    @Test
    public void testGetApplication2() throws Exception {
        List<ApplicationVO> applicationVOS = new ArrayList<>();
        ApplicationVO applicationVO = new ApplicationVO();
        applicationVO.setApplicationId(11);
        applicationVO.setApplicationName("L&T");
        applicationVO.setDescription("IT company");
        applicationVO.setOwner("Jeff");
        applicationVOS.add(applicationVO);
        when(applicationService.findAll()).thenReturn(null);
        // create the request hit the end point
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications")
                .contentType(MediaType.APPLICATION_JSON);
        // pass the request and return the request
        MvcResult mvcResult = mockMVC.perform(requestBuilder).andReturn();
        // get the response of body
        MockHttpServletResponse response = mvcResult.getResponse();
        // We get the status code of body and verify the expected and actual result
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    // positive 200 status code
    @Test
    public void testgetApplicationById() throws Exception {
        ApplicationVO applicationVO = new ApplicationVO();
        applicationVO.setApplicationId(11);
        applicationVO.setApplicationName("L&T");
        applicationVO.setDescription("IT company");
        applicationVO.setOwner("Jeff");

    //    when(applicationService.getApplicationId(anyLong())).thenReturn(applicationVO);
        // create the request hit the end point
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications/11")
                .contentType(MediaType.APPLICATION_JSON);
        mockMVC.perform(requestBuilder).andExpect(status().isOk());
    }

    // Negative 500 status code
    @Test
    public void testgetApplicationById1() throws Exception {
        ApplicationVO applicationVO = new ApplicationVO();
        applicationVO.setApplicationId(11);
        applicationVO.setApplicationName("L&T");
        applicationVO.setDescription("IT company");
        applicationVO.setOwner("Jeff");

        when(applicationService.getApplicationId(anyLong())).thenThrow(new NullPointerException());
        // create the request hit the end point
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications/11")
                .contentType(MediaType.APPLICATION_JSON);
        mockMVC.perform(requestBuilder).andExpect(status().isInternalServerError());
    }

    // 404 not found
    @Test
    public void testgetApplicationById2() throws Exception {
        ApplicationVO applicationVO = new ApplicationVO();
        applicationVO.setApplicationId(11);
        applicationVO.setApplicationName("L&T");
        applicationVO.setDescription("IT company");
        applicationVO.setOwner("Jeff");

        when(applicationService.getApplicationId(anyLong())).thenReturn(null);
        // create the request hit the end point
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications/11")
                .contentType(MediaType.APPLICATION_JSON);
        mockMVC.perform(requestBuilder).andExpect(status().isNotFound());
    }

    // positive 200 status code
    @Test
    public void testgetApplicationByName() throws Exception {
        ApplicationVO applicationVO = new ApplicationVO();
        applicationVO.setApplicationId(11);
        applicationVO.setApplicationName("LT");
        applicationVO.setDescription("IT company");
        applicationVO.setOwner("Jeff");

      //  when(applicationService.findByName(anyString())).thenReturn(applicationVO);
        // create the request hit the end point
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications/name?name=LT&id=11")
            //    .queryParam("LT", "LT")
              //  .queryParam("id", String.valueOf(11))
                .contentType(MediaType.APPLICATION_JSON);
        mockMVC.perform(requestBuilder).andExpect(status().isOk());
    }

    // 404 status code
    @Test
    public void testgetApplicationByName1() throws Exception {
        ApplicationVO applicationVO = new ApplicationVO();
        applicationVO.setApplicationId(11);
        applicationVO.setApplicationName("L&T");
        applicationVO.setDescription("IT company");
        applicationVO.setOwner("Jeff");

        when(applicationService.findByName(any())).thenReturn(null);
        // create the request hit the end point
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications/name?name=L&T&id=11")
                .contentType(MediaType.APPLICATION_JSON);
        mockMVC.perform(requestBuilder).andExpect(status().isNotFound());
    }

    // 500 status code
    @Test
    public void testgetApplicationByName2() throws Exception {
        ApplicationVO applicationVO = new ApplicationVO();
        applicationVO.setApplicationId(11);
        applicationVO.setApplicationName("L&T");
        applicationVO.setDescription("IT company");
        applicationVO.setOwner("Jeff");

        when(applicationService.findByName(any())).thenThrow(new NullPointerException());
        // create the request hit the end point
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/applications/name?name=L&T&id=11")
                .contentType(MediaType.APPLICATION_JSON);
        mockMVC.perform(requestBuilder).andExpect(status().isInternalServerError());
    }

    // 200 positive test case
    @Test
    public void testSaveApplication() throws Exception{
        ApplicationVO applicationVO =new ApplicationVO();
        applicationVO.setApplicationId(1);
        applicationVO.setApplicationName("LRI");
        applicationVO.setDescription("LRI");
        applicationVO.setOwner("JPMC");

        when(applicationService.save(any())).thenReturn(applicationVO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/applications")
                .content(asJsonString(applicationVO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMVC.perform(requestBuilder).andExpect(status().isOk());

    }

    //404 not found test case
    @Test
    public void testSaveApplication1() throws Exception{
        ApplicationVO applicationVO =new ApplicationVO();
        applicationVO.setApplicationId(1);
        applicationVO.setApplicationName("LRI");
        applicationVO.setDescription("LRI");
        applicationVO.setOwner("JPMC");

        when(applicationService.save(any())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/applications")
                .content(asJsonString(applicationVO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMVC.perform(requestBuilder).andExpect(status().isNotFound());
    }

    //500 internal server error test case
    @Test
    public void testSaveApplication2() throws Exception{
        ApplicationVO applicationVO =new ApplicationVO();
        applicationVO.setApplicationId(1);
        applicationVO.setApplicationName("LRI");
        applicationVO.setDescription("LRI");
        applicationVO.setOwner("JPMC");

        when(applicationService.save(any())).thenThrow(new NullPointerException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/applications")
                .content(asJsonString(applicationVO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMVC.perform(requestBuilder).andExpect(status().isInternalServerError());

    }

    //504 bad request test case
    @Test
    public void testSaveApplication3() throws Exception{
        ApplicationVO applicationVO = null;

        when(applicationService.save(any())).thenReturn(applicationVO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/applications")
                .content(asJsonString(applicationVO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMVC.perform(requestBuilder).andExpect(status().isBadRequest());

    }


    // 200 positive test case
    @Test
    public void testUpdateApplication() throws Exception{
        ApplicationVO applicationVO =new ApplicationVO();
        applicationVO.setApplicationId(1);
        applicationVO.setApplicationName("LRI");
        applicationVO.setDescription("LRI");
        applicationVO.setOwner("JPMC");

        when(applicationService.save(any())).thenReturn(applicationVO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/applications")
                .content(asJsonString(applicationVO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMVC.perform(requestBuilder).andExpect(status().isOk());

    }

    //404 not found test case
    @Test
    public void testUpdateApplication1() throws Exception{
        ApplicationVO applicationVO =new ApplicationVO();
        applicationVO.setApplicationId(1);
        applicationVO.setApplicationName("LRI");
        applicationVO.setDescription("LRI");
        applicationVO.setOwner("JPMC");

        when(applicationService.save(any())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/applications")
                .content(asJsonString(applicationVO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMVC.perform(requestBuilder).andExpect(status().isNotFound());
    }

    //500 internal server error test case
    @Test
    public void testUpdateApplication2() throws Exception{
        ApplicationVO applicationVO =new ApplicationVO();
        applicationVO.setApplicationId(1);
        applicationVO.setApplicationName("LRI");
        applicationVO.setDescription("LRI");
        applicationVO.setOwner("JPMC");

        when(applicationService.save(any())).thenThrow(new NullPointerException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/applications")
                .content(asJsonString(applicationVO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMVC.perform(requestBuilder).andExpect(status().isInternalServerError());

    }

    //504 bad request test case
    @Test
    public void testUpdateApplication3() throws Exception{
        ApplicationVO applicationVO = null;

        when(applicationService.save(any())).thenReturn(applicationVO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/applications")
                .content(asJsonString(applicationVO))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMVC.perform(requestBuilder).andExpect(status().isBadRequest());

    }

  /*  // 404 Not found test case
    @Test
    public void testDeleteApplicationById() throws Exception{
        when(applicationService.delete(anyLong())).thenThrow(new NullPointerException());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/application/1")
                 .contentType(MediaType.APPLICATION_JSON);
        mockMVC.perform(requestBuilder).andExpect(status().isInternalServerError());

    }*/

    // 404 not found test case
    @Test
    public void testDeleteApplicationById2() throws Exception{
      //  ApplicationVO applicationVO =new ApplicationVO();
        when(applicationService.delete(anyLong())).thenThrow(new NullPointerException());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/applications/45")
                .contentType(MediaType.APPLICATION_JSON);
        mockMVC.perform(requestBuilder).andExpect(status().isNotFound());

    }

    // 200 positive test case
    @Test
    public void testDeleteApplicationById1() throws Exception{
          ApplicationVO applicationVO = new ApplicationVO();
          when(applicationService.delete(anyLong())).thenReturn("applicationVO");
          RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/applications/45")
                .contentType(MediaType.APPLICATION_JSON);
         mockMVC.perform(requestBuilder).andExpect(status().isOk());

    }


    public String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
