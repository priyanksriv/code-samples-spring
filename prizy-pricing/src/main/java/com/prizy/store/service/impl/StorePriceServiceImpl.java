package com.prizy.store.service.impl;

import com.prizy.store.domain.entity.StorePrice;
import com.prizy.store.domain.repository.StorePriceRepository;
import com.prizy.store.mapper.StorePriceToStorePriceVOMapper;
import com.prizy.store.mapper.StorePriceVOToStorePriceMapper;
import com.prizy.store.service.StorePriceService;
import com.prizy.store.vo.StorePriceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


@Service
public class StorePriceServiceImpl implements StorePriceService {

    private Logger LOG = LoggerFactory.getLogger(StorePriceService.class);

    @Transactional
    @Override
    public StorePriceVO saveStorePrice(StorePriceVO storePriceVO) {
        storePriceVO.setId(UUID.randomUUID().toString());
        StorePrice storePrice = storePriceRepository.save(
                storePriceVOToStorePriceMapper.map(storePriceVO));

        LOG.info("Saved StorePrice: {}", storePrice);
        return storePriceToStorePriceVOMapper.map(storePrice);
    }

    @Override
    public List<StorePriceVO> findByProductId(String productId) {
        List<StorePrice> prices = storePriceRepository.findByProductId(productId);

        LOG.info("Found store price entries: {}", prices.size());
        return storePriceToStorePriceVOMapper.mapList(prices);
    }

    @Autowired
    private StorePriceRepository storePriceRepository;

    @Autowired
    private StorePriceToStorePriceVOMapper storePriceToStorePriceVOMapper;

    @Autowired
    private StorePriceVOToStorePriceMapper storePriceVOToStorePriceMapper;
}
