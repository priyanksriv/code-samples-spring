package com.prizy.store.service.impl;

import com.prizy.common.exception.RecordNotFoundException;
import com.prizy.store.domain.entity.Store;
import com.prizy.store.domain.repository.StoreRepository;
import com.prizy.store.mapper.StoreToStoreVOMapper;
import com.prizy.store.mapper.StoreVOToStoreMapper;
import com.prizy.store.service.StoreService;
import com.prizy.store.vo.StoreVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


@Service
public class StoreServiceImpl implements StoreService {

    private Logger LOG = LoggerFactory.getLogger(StoreService.class);

    @Override
    public List<StoreVO> findAllStores() {
        Iterable<Store> entities = storeRepository.findAll();
        return storeToStoreVOMapper.mapList(entities);
    }

    @Transactional
    @Override
    public StoreVO createStore(StoreVO storeVO) {
        storeVO.setId(UUID.randomUUID().toString());
        Store store = storeRepository.save(storeVOToStoreMapper.map(storeVO));

        LOG.info("Saved Store: {}", store);
        return storeToStoreVOMapper.map(store);
    }

    @Override
    public StoreVO findStore(String storeId) {
        Store store = storeRepository.findOne(storeId);
        if (store == null) {
            LOG.error("Store not found with ID: {}", storeId);
            throw new RecordNotFoundException("Store not found.");
        }
        LOG.info("Returning Store: {}", store);
        return storeToStoreVOMapper.map(store);
    }

    @Transactional
    @Override
    public StoreVO updateStore(String storeId, StoreVO storeVO) {
        Store oldStore = storeRepository.findOne(storeVO.getId());
        if (oldStore == null) {
            LOG.error("Store not found with ID: {}", storeId);
            throw new RecordNotFoundException("Fatal: Cannot update store that doesn't exist.");
        }
        storeVO.setId(storeId);
        Store store = storeRepository.save(storeVOToStoreMapper.map(storeVO));

        LOG.info("ID: {}, Updated store: {}", storeId, store);
        return storeToStoreVOMapper.map(store);
    }

    @Transactional
    @Override
    public StoreVO deleteStore(String storeId) {
        Store store = storeRepository.findOne(storeId);
        if (store == null) {
            LOG.error("Store not found with ID: {}", storeId);
            throw new RecordNotFoundException("Fatal: Cannot delete store that doesn't exist.");
        }
        storeRepository.delete(store);

        LOG.info("ID: {}, Deleted store: {}", storeId, store);
        return storeToStoreVOMapper.map(store);
    }

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreToStoreVOMapper storeToStoreVOMapper;

    @Autowired
    private StoreVOToStoreMapper storeVOToStoreMapper;
}
