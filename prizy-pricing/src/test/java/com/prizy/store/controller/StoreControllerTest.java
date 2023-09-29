package com.prizy.store.controller;

import com.prizy.store.service.StorePriceService;
import com.prizy.store.service.StoreService;
import com.prizy.store.vo.StorePriceVO;
import com.prizy.store.vo.StoreVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(StoreController.class)
public class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @MockBean
    private StorePriceService storePriceService;

    @Test
    public void testFindAllStores() throws Exception {
        List<StoreVO> vos = new ArrayList<>();
        String id = UUID.randomUUID().toString();

        StoreVO vo = new StoreVO();
        vo.setId(id);
        vos.add(vo);

        when(storeService.findAllStores()).thenReturn(vos);

        RequestBuilder reqBuilder = get("/api/v1/store")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(reqBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String expected = "[{ \"id\": " + id + " }]";
        String contentResult = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expected, contentResult, false);

        verify(storeService, times(1)).findAllStores();
    }

    @Test
    public void testFindStore() throws Exception {
        String id = UUID.randomUUID().toString();
        StoreVO vo = new StoreVO();
        vo.setId(id);

        when(storeService.findStore(anyString())).thenReturn(vo);

        RequestBuilder reqBuilder = get("/api/v1/store/" + id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(reqBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{ \"id\": " + id + " }";
        String contentResult = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expected, contentResult, false);

        verify(storeService, times(1)).findStore(anyString());
    }

    @Test
    public void testCreateStore() throws Exception {
        String id = UUID.randomUUID().toString();
        StoreVO vo = new StoreVO();
        vo.setStoreName("Store1");
        vo.setId(id);

        String reqContent = "{ \"storeName\": \"Store1\" }";

        when(storeService.createStore(any(StoreVO.class))).thenReturn(vo);

        RequestBuilder reqBuilder = post("/api/v1/store/")
                .accept(MediaType.APPLICATION_JSON).content(reqContent)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(reqBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertTrue(response.getHeader(HttpHeaders.LOCATION)
                .endsWith("/api/v1/store/" + id));

        verify(storeService, times(1)).createStore(any(StoreVO.class));
    }

    @Test
    public void testSaveStorePrice() throws Exception {
        String store = UUID.randomUUID().toString();
        String product = UUID.randomUUID().toString();
        String id  = UUID.randomUUID().toString();

        StorePriceVO vo = new StorePriceVO();
        vo.setId(id);
        vo.setStore(store);
        vo.setProduct(product);
        vo.setPrice(2200.0);

        String reqContent = "{ \"product\": \"" + product + "\", \"store\": \"" + store +
                "\", \"price\": 2200.0 }";

        when(storePriceService.saveStorePrice(any(StorePriceVO.class))).thenReturn(vo);

        RequestBuilder reqBuilder = post("/api/v1/store/product")
                .accept(MediaType.APPLICATION_JSON).content(reqContent)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(reqBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertTrue(response.getHeader(HttpHeaders.LOCATION)
                .endsWith("/api/v1/store/product/" + id));

        verify(storePriceService, times(1)).saveStorePrice(any(StorePriceVO.class));
    }
}
