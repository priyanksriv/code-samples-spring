package com.prizy.store.mapper;

import com.prizy.store.domain.entity.Store;
import com.prizy.store.vo.StoreVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class StoreToStoreVOMapper {

    public StoreVO map(Store entity) {
        StoreVO vo = new StoreVO();
        vo.setId(entity.getId().toString());
        vo.setStoreName(entity.getStoreName());
        vo.setDescription(entity.getDescription());
        vo.setCreated(entity.getCreated());
        return vo;
    }

    public List<StoreVO> mapList(Iterable<Store> entities) {
        List<StoreVO> vos = new ArrayList<>();
        for (Store entity : entities) {
            vos.add(map(entity));
        }
        return vos;
    }
}
