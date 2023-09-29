package com.prizy.store.service;

import com.prizy.product.domain.entity.Product;
import com.prizy.store.domain.entity.Store;
import com.prizy.store.domain.entity.StorePrice;
import com.prizy.store.domain.repository.StorePriceRepository;
import com.prizy.store.mapper.StorePriceToStorePriceVOMapper;
import com.prizy.store.mapper.StorePriceVOToStorePriceMapper;
import com.prizy.store.service.impl.StorePriceServiceImpl;
import com.prizy.store.vo.StorePriceVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StorePriceServiceTest {

    @InjectMocks
    private StorePriceServiceImpl storePriceService;

    @Mock
    private StorePriceRepository storePriceRepository;

    @Mock
    private StorePriceToStorePriceVOMapper storePriceToStorePriceVOMapper;

    @Mock
    private StorePriceVOToStorePriceMapper storePriceVOToStorePriceMapper;

    @Test
    public void testCreateStorePrice() {
        String storeId = UUID.randomUUID().toString();
        Store store = new Store();
        store.setId(storeId);

        String productId = UUID.randomUUID().toString();
        Product product = new Product();
        product.setId(productId);

        Double price = 2099.5;

        StorePriceVO storePriceVO1 = new StorePriceVO();
        StorePrice storePrice = new StorePrice();

        StorePrice saved = new StorePrice();
        saved.setStore(store);
        saved.setProduct(product);
        saved.setPrice(price);

        StorePriceVO storePriceVO2 = new StorePriceVO();
        storePriceVO2.setStore(storeId);
        storePriceVO2.setProduct(productId);
        storePriceVO2.setPrice(price);

        when(storePriceVOToStorePriceMapper.map(storePriceVO1)).thenReturn(storePrice);
        when(storePriceRepository.save(storePrice)).thenReturn(saved);
        when(storePriceToStorePriceVOMapper.map(saved)).thenReturn(storePriceVO2);

        StorePriceVO result = storePriceService.saveStorePrice(storePriceVO1);
        verify(storePriceRepository, times(1)).save(storePrice);

        assertEquals(result.getStore(), saved.getStore().getId());
        assertEquals(result.getProduct(), saved.getProduct().getId());
        assertEquals(result.getPrice(), saved.getPrice());
    }

    @Test
    public void testFindByProductId() {
        List<StorePrice> entities = new ArrayList<>();
        List<StorePriceVO> vos = new ArrayList<>();

        when(storePriceRepository.findByProductId(anyString())).thenReturn(entities);
        when(storePriceToStorePriceVOMapper.mapList(entities)).thenReturn(vos);

        List<StorePriceVO> result = storePriceService.findByProductId(UUID.randomUUID().toString());
        assertSame(result, vos);
    }
}
