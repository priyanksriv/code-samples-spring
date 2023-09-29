package com.prizy.store.mapper;

import com.prizy.store.domain.entity.StorePrice;
import com.prizy.store.vo.StorePriceVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class StorePriceToStorePriceVOMapper {

    public StorePriceVO map(StorePrice storePrice) {
        StorePriceVO storePriceVO = new StorePriceVO();
        storePriceVO.setId(storePrice.getId());

        if (storePrice.getProduct() != null) {
            storePriceVO.setProduct(storePrice.getProduct().getId());
        }

        if (storePrice.getStore() != null) {
            storePriceVO.setStore(storePrice.getStore().getId());
        }

        storePriceVO.setPrice(storePrice.getPrice());
        storePriceVO.setNotes(storePrice.getNotes());
        storePriceVO.setCreated(storePrice.getCreated());

        return storePriceVO;
    }

    public List<StorePriceVO> mapList(Iterable<StorePrice> entities) {
        List<StorePriceVO> vos = new ArrayList<>();
        for (StorePrice entity : entities) {
            vos.add(map(entity));
        }
        return vos;
    }
}
