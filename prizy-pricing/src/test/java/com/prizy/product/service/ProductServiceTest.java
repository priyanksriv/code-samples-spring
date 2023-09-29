package com.prizy.product.service;

import com.prizy.common.exception.RecordNotFoundException;
import com.prizy.product.domain.entity.PricingDetails;
import com.prizy.product.domain.entity.Product;
import com.prizy.product.domain.repository.PricingDetailsRepository;
import com.prizy.product.domain.repository.ProductRepository;
import com.prizy.product.mapper.ProductToProductVOMapper;
import com.prizy.product.mapper.ProductVOToProductMapper;
import com.prizy.product.service.impl.ProductServiceImpl;
import com.prizy.product.vo.PricingDetailsVO;
import com.prizy.product.vo.ProductVO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PricingDetailsRepository pricingDetailsRepository;

    @Mock
    private ProductToProductVOMapper productToProductVOMapper;

    @Mock
    private ProductVOToProductMapper productVOToProductMapper;

    @Test
    public void testFindAllProducts() {
        List<Product> entities = new ArrayList<>();
        List<ProductVO> vos = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(entities);
        when(productToProductVOMapper.mapList(entities)).thenReturn(vos);

        List<ProductVO> result = productService.findAllProducts();
        assertSame(result, vos);
    }

    @Test
    public void testFindProduct() {
        String id = UUID.randomUUID().toString();

        Product product = new Product();
        product.setId(id);

        ProductVO productVO = new ProductVO();
        productVO.setId(id);

        when(productRepository.findOne(id)).thenReturn(product);
        when(productToProductVOMapper.map(product)).thenReturn(productVO);

        ProductVO result = productService.findProduct(id);
        assertSame(result, productVO);
        assertEquals(result.getId(), id);
    }

    @Test
    public void testFindProduct_NotFound() {
        String id = UUID.randomUUID().toString();

        when(productRepository.findOne(id)).thenReturn(null);

        thrown.expect(RecordNotFoundException.class);
        thrown.expectMessage("Product not found.");

        productService.findProduct(id);
    }

    @Test
    public void testCreateProduct() {
        String productName = "Product1";

        ProductVO productVO1 = new ProductVO();
        Product product = new Product();
        Product saved = new Product();
        saved.setProductName(productName);
        ProductVO productVO2 = new ProductVO();
        productVO2.setProductName(productName);

        when(productVOToProductMapper.map(productVO1)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(saved);
        when(productToProductVOMapper.map(saved)).thenReturn(productVO2);

        ProductVO result = productService.createProduct(productVO1);
        verify(productRepository, times(1)).save(product);
        assertEquals(result.getProductName(), saved.getProductName());
    }

    @Test
    public void testUpdateProduct() {
        String id = UUID.randomUUID().toString();

        ProductVO productVO1 = new ProductVO();
        productVO1.setId(id);
        productVO1.setProductName("Product1");
        productVO1.setBasePrice(2199.0);

        Product oldProduct = new Product();
        oldProduct.setId(id);
        oldProduct.setProductName("Product1");
        oldProduct.setBasePrice(2099.0);

        ProductVO productVO2 = new ProductVO();
        productVO2.setProductName("Product1");
        productVO2.setBasePrice(2199.0);

        Product product1 = new Product();
        product1.setId(id);
        product1.setProductName(productVO1.getProductName());
        product1.setBasePrice(productVO1.getBasePrice());

        Product product2 = new Product();
        product2.setId(id);
        product2.setProductName(productVO1.getProductName());
        product2.setBasePrice(productVO1.getBasePrice());

        when(productRepository.findOne(id)).thenReturn(oldProduct);
        when(productVOToProductMapper.map(productVO1)).thenReturn(product1);
        when(productRepository.save(product1)).thenReturn(product2);
        when(productToProductVOMapper.map(product2)).thenReturn(productVO2);

        ProductVO result = productService.updateProduct(id, productVO1);
        verify(productRepository, times(1)).save(product1);

        assertEquals("Name not changed", result.getProductName(), productVO2.getProductName());
        assertEquals("Changed base price", result.getBasePrice(), productVO2.getBasePrice());
    }

    @Test
    public void testUpdateProduct_NotFound() {
        String id = UUID.randomUUID().toString();
        ProductVO productVO = new ProductVO();

        //when(productRepository.findOne(id)).thenReturn(null);

        thrown.expect(RecordNotFoundException.class);
        thrown.expectMessage("Fatal: Cannot update product that doesn't exist.");

        productService.updateProduct(id, productVO);
    }

    @Test
    public void testDeleteProduct() {
        String id = UUID.randomUUID().toString();
        Product product = new Product();
        product.setId(id);

        when(productRepository.findOne(id)).thenReturn(product);
        productService.deleteProduct(id);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testDeleteProduct_NotFound() {
        String id = UUID.randomUUID().toString();

        when(productRepository.findOne(id)).thenReturn(null);

        thrown.expect(RecordNotFoundException.class);
        thrown.expectMessage("Fatal: Cannot delete product that doesn't exist.");

        productService.deleteProduct(id);
    }

    @Test
    public void testGetPrices_NotFound() {
        String id = UUID.randomUUID().toString();

        when(productRepository.findOne(id)).thenReturn(null);

        thrown.expect(RecordNotFoundException.class);
        thrown.expectMessage("Product doesn't exist.");

        productService.getPrices(id);
    }

    @Test
    public void testGetPrices() {
        String id = UUID.randomUUID().toString();
        Product product = new Product();
        product.setId(id);
        product.setBasePrice(2319.0);
        product.setProductName("Product-1");

        PricingDetails pricingDetails = new PricingDetails();
        pricingDetails.setProduct(id);
        pricingDetails.setIdealPrice(2500.0);
        pricingDetails.setAveragePrice(2311.0);
        pricingDetails.setLowestPrice(2234.5);
        pricingDetails.setHighestPrice(2679.0);

        when(productRepository.findOne(id)).thenReturn(product);
        when(pricingDetailsRepository.findLatestByProductId(anyString()))
                .thenReturn(pricingDetails);

        PricingDetailsVO result = productService.getPrices(id);

        assertEquals(result.getName(), product.getProductName());
        assertEquals(result.getBasePrice(), product.getBasePrice());
        assertEquals(result.getIdealPrice(), pricingDetails.getIdealPrice());
        assertEquals(result.getAveragePrice(), pricingDetails.getAveragePrice());
        assertEquals(result.getLowestPrice(), pricingDetails.getLowestPrice());
        assertEquals(result.getHighestPrice(), pricingDetails.getHighestPrice());
    }
}
