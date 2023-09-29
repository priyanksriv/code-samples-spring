package com.prizy.product.controller;

import com.prizy.product.service.JobsService;
import com.prizy.product.vo.JobVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(JobsController.class)
public class JobsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobsService jobsService;

    @Test
    public void testStartCalculatorJob() throws Exception {
        JobVO jobVO = new JobVO();
        jobVO.setJob("price-calculation");

        when(jobsService.startPriceCalculatorJob(anyString())).thenReturn(jobVO);

        RequestBuilder reqBuilder = post("/api/v1/jobs/priceCalculator")
                .param("command", "start")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(reqBuilder)
                .andExpect(status().isAccepted())
                .andReturn();

        String contentResult = result.getResponse().getContentAsString();
        String expected = "{ \"job\": \"price-calculation\" }";
        JSONAssert.assertEquals(expected, contentResult, false);

        verify(jobsService, times(1)).startPriceCalculatorJob(anyString());
    }
}
