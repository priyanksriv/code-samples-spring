package com.prizy.store.mapper;

import com.prizy.product.domain.entity.Product;
import com.prizy.store.domain.entity.Store;
import com.prizy.store.domain.entity.StorePrice;
import com.prizy.store.vo.StorePriceVO;
import org.springframework.stereotype.Component;


@Component
public class StorePriceVOToStorePriceMapper {

    public StorePrice map(StorePriceVO storePriceVO) {
        StorePrice storePrice = new StorePrice();
        storePrice.setId(storePriceVO.getId());

        Product product = new Product();
        product.setId(storePriceVO.getProduct());
        storePrice.setProduct(product);

        Store store = new Store();
        store.setId(storePriceVO.getStore());
        storePrice.setStore(store);

        storePrice.setPrice(storePriceVO.getPrice());
        storePrice.setNotes(storePriceVO.getNotes());
        storePrice.setCreated(storePriceVO.getCreated());

        return storePrice;
    }
}
