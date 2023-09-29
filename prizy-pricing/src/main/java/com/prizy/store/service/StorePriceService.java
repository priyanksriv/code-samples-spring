package com.prizy.store.service;


import com.prizy.store.vo.StorePriceVO;

import java.util.List;

public interface StorePriceService {

    StorePriceVO saveStorePrice(StorePriceVO storePriceVO);

    List<StorePriceVO> findByProductId(String productId);
}
