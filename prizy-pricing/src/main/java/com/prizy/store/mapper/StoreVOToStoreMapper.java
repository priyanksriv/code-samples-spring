package com.prizy.store.mapper;

import com.prizy.store.domain.entity.Store;
import com.prizy.store.vo.StoreVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class StoreVOToStoreMapper {

    public Store map(StoreVO vo) {
        Store entity = new Store();
        entity.setId(vo.getId());
        entity.setStoreName(vo.getStoreName());
        entity.setDescription(vo.getDescription());
        entity.setCreated(vo.getCreated());
        return entity;
    }

    public List<Store> mapList(Iterable<StoreVO> vos) {
        List<Store> entities = new ArrayList<>();
        for (StoreVO vo : vos) {
            entities.add(map(vo));
        }
        return entities;
    }
}
