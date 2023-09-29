package com.prizy.store.service;

import com.prizy.common.exception.RecordNotFoundException;
import com.prizy.store.domain.entity.Store;
import com.prizy.store.domain.repository.StoreRepository;
import com.prizy.store.mapper.StoreToStoreVOMapper;
import com.prizy.store.mapper.StoreVOToStoreMapper;
import com.prizy.store.service.impl.StoreServiceImpl;
import com.prizy.store.vo.StoreVO;
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
public class StoreServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private StoreServiceImpl storeService;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private StoreToStoreVOMapper storeToStoreVOMapper;

    @Mock
    private StoreVOToStoreMapper storeVOToStoreMapper;

    @Test
    public void testFindAllProducts() {
        List<Store> entities = new ArrayList<>();
        List<StoreVO> vos = new ArrayList<>();

        when(storeRepository.findAll()).thenReturn(entities);
        when(storeToStoreVOMapper.mapList(entities)).thenReturn(vos);

        List<StoreVO> result = storeService.findAllStores();
        assertSame(result, vos);
    }

    @Test
    public void testFindStore() {
        String id = UUID.randomUUID().toString();

        Store store = new Store();
        store.setId(id);

        StoreVO storeVO = new StoreVO();
        storeVO.setId(id);

        when(storeRepository.findOne(id)).thenReturn(store);
        when(storeToStoreVOMapper.map(store)).thenReturn(storeVO);

        StoreVO result = storeService.findStore(id);
        assertSame(result, storeVO);
        assertEquals(result.getId(), id);
    }

    @Test
    public void testFindStore_NotFound() {
        String id = UUID.randomUUID().toString();

        when(storeRepository.findOne(id)).thenReturn(null);

        thrown.expect(RecordNotFoundException.class);
        thrown.expectMessage("Store not found.");

        storeService.findStore(id);
    }

    @Test
    public void testCreateStore() {
        String storeName = "Store1";

        StoreVO storeVO1 = new StoreVO();
        Store store = new Store();
        Store saved = new Store();
        saved.setStoreName(storeName);
        StoreVO storeVO2 = new StoreVO();
        storeVO2.setStoreName(storeName);

        when(storeVOToStoreMapper.map(storeVO1)).thenReturn(store);
        when(storeRepository.save(store)).thenReturn(saved);
        when(storeToStoreVOMapper.map(saved)).thenReturn(storeVO2);

        StoreVO result = storeService.createStore(storeVO1);
        verify(storeRepository, times(1)).save(store);
        assertEquals(result.getStoreName(), saved.getStoreName());
    }

    @Test
    public void testUpdateStore() {
        String id = UUID.randomUUID().toString();

        StoreVO storeVO1 = new StoreVO();
        storeVO1.setId(id);
        storeVO1.setStoreName("Store2");

        Store oldStore = new Store();
        oldStore.setId(id);
        oldStore.setStoreName("Store1");

        StoreVO storeVO2 = new StoreVO();
        storeVO2.setStoreName("Store2");

        Store store1 = new Store();
        store1.setId(id);
        store1.setStoreName(storeVO1.getStoreName());

        Store store2 = new Store();
        store2.setId(id);
        store2.setStoreName(storeVO1.getStoreName());

        when(storeRepository.findOne(id)).thenReturn(oldStore);
        when(storeVOToStoreMapper.map(storeVO1)).thenReturn(store1);
        when(storeRepository.save(store1)).thenReturn(store2);
        when(storeToStoreVOMapper.map(store2)).thenReturn(storeVO2);

        StoreVO result = storeService.updateStore(id, storeVO1);
        verify(storeRepository, times(1)).save(store1);

        assertEquals(result.getStoreName(), storeVO2.getStoreName());
    }

    @Test
    public void testUpdateStore_NotFound() {
        String id = UUID.randomUUID().toString();
        StoreVO storeVO = new StoreVO();

        //when(storeRepository.findOne(id)).thenReturn(null);

        thrown.expect(RecordNotFoundException.class);
        thrown.expectMessage("Fatal: Cannot update store that doesn't exist.");

        storeService.updateStore(id, storeVO);
    }

    @Test
    public void testDeleteStore() {
        String id = UUID.randomUUID().toString();
        Store store = new Store();
        store.setId(id);

        when(storeRepository.findOne(id)).thenReturn(store);
        storeService.deleteStore(id);
        verify(storeRepository, times(1)).delete(store);
    }

    @Test
    public void testDeleteStore_NotFound() {
        String id = UUID.randomUUID().toString();

        when(storeRepository.findOne(id)).thenReturn(null);

        thrown.expect(RecordNotFoundException.class);
        thrown.expectMessage("Fatal: Cannot delete store that doesn't exist.");

        storeService.deleteStore(id);
    }

}
