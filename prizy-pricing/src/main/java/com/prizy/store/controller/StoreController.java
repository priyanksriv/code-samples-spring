package com.prizy.store.controller;

import com.prizy.store.controller.api.StoreAPI;
import com.prizy.store.service.StorePriceService;
import com.prizy.store.service.StoreService;
import com.prizy.store.vo.StorePriceVO;
import com.prizy.store.vo.StoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "api/v1/store", produces = MediaType.APPLICATION_JSON_VALUE)
public class StoreController implements StoreAPI {

    @GetMapping
    @Override
    public ResponseEntity<List<StoreVO>> findAllStores() {
        return ResponseEntity.ok(storeService.findAllStores());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Void> createStore(@RequestBody StoreVO storeVO) {
        StoreVO savedVO = storeService.createStore(storeVO);
        String id = savedVO.getId();
        return ResponseEntity.created(location("/{id}", id))
                .header("Id", id).build();
    }

    @GetMapping(value = "/{storeId}")
    @Override
    public ResponseEntity<StoreVO> findStore(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.findStore(storeId));
    }

    @PutMapping(value = "/{storeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Void> updateStore(@PathVariable String storeId,
            @RequestBody StoreVO storeVO) {
        storeService.updateStore(storeId, storeVO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{storeId}")
    @Override
    public ResponseEntity<StoreVO> deleteStore(String storeId) {
        return ResponseEntity.ok(storeService.deleteStore(storeId));
    }

    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<StorePriceVO> saveStorePrice(@RequestBody StorePriceVO storePriceVO) {
        StorePriceVO savedVO = storePriceService.saveStorePrice(storePriceVO);
        String id = savedVO.getId();
        return ResponseEntity.created(location("/{id}", id))
                .header("Id", id).body(savedVO);
    }

    private static URI location(String path, Object... args) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path).buildAndExpand(args).toUri();
    }

    @Autowired
    private StoreService storeService;

    @Autowired
    private StorePriceService storePriceService;
}