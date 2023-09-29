package com.prizy.product.controller;

import com.prizy.product.service.ProductService;
import com.prizy.product.vo.PricingDetailsVO;
import com.prizy.product.vo.ProductVO;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testFindAllProducts() throws Exception {
        List<ProductVO> vos = new ArrayList<>();
        String id = UUID.randomUUID().toString();

        ProductVO vo = new ProductVO();
        vo.setId(id);
        vos.add(vo);

        when(productService.findAllProducts()).thenReturn(vos);

        RequestBuilder reqBuilder = get("/api/v1/product")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(reqBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String expected = "[{ \"id\": " + id + " }]";
        String contentResult = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expected, contentResult, false);

        verify(productService, times(1)).findAllProducts();
    }

    @Test
    public void testFindProduct() throws Exception {
        String id = UUID.randomUUID().toString();
        ProductVO vo = new ProductVO();
        vo.setId(id);

        when(productService.findProduct(anyString())).thenReturn(vo);

        RequestBuilder reqBuilder = get("/api/v1/product/" + id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(reqBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{ \"id\": " + id + " }";
        String contentResult = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expected, contentResult, false);

        verify(productService, times(1)).findProduct(anyString());
    }

    @Test
    public void testCreateProduct() throws Exception {
        String id = UUID.randomUUID().toString();
        ProductVO vo = new ProductVO();
        vo.setProductName("Product1");
        vo.setId(id);

        String reqContent = "{ \"productName\": \"Product1\" }";

        when(productService.createProduct(any(ProductVO.class))).thenReturn(vo);

        RequestBuilder reqBuilder = post("/api/v1/product/")
                .accept(MediaType.APPLICATION_JSON).content(reqContent)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(reqBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertTrue(response.getHeader(HttpHeaders.LOCATION)
                .endsWith("/api/v1/product/" + id));

        verify(productService, times(1)).createProduct(any(ProductVO.class));
    }

    @Test
    public void testGetPrices() throws Exception {
        String id = UUID.randomUUID().toString();
        PricingDetailsVO pricingDetailsVO = new PricingDetailsVO();
        pricingDetailsVO.setProduct(id);
        pricingDetailsVO.setIdealPrice(2602.0);
        pricingDetailsVO.setBasePrice(2179.0);
        pricingDetailsVO.setLowestPrice(2099.0);
        pricingDetailsVO.setHighestPrice(2288.0);

        when(productService.getPrices(anyString())).thenReturn(pricingDetailsVO);

        RequestBuilder reqBuilder = get("/api/v1/product/" + id + "/prices")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(reqBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{ \"product\": \"" + id + "\", \"basePrice\": 2179.0, " +
                "\"idealPrice\": 2602.0, \"lowestPrice\": 2099.0, \"highestPrice\": 2288.0 }";
        String contentResult = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expected, contentResult, false);

        verify(productService, times(1)).getPrices(anyString());
    }
}
