package com.prizy.product.controller;

import com.prizy.product.controller.api.ProductAPI;
import com.prizy.product.service.ProductService;
import com.prizy.product.vo.PricingDetailsVO;
import com.prizy.product.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "api/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController implements ProductAPI {

    @GetMapping
    @Override
    public ResponseEntity<List<ProductVO>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Void> createProduct(@RequestBody ProductVO productVO) {
        ProductVO savedVO = productService.createProduct(productVO);
        String id = savedVO.getId();
        return ResponseEntity.created(location("/{id}", id))
                .header("Id", id).build();
    }

    @GetMapping(value = "/{productId}")
    @Override
    public ResponseEntity<ProductVO> findProduct(@PathVariable String productId) {
        return ResponseEntity.ok(productService.findProduct(productId));
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Void> updateProduct(@PathVariable String productId,
            @RequestBody ProductVO productVO) {
        productService.updateProduct(productId, productVO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{productId}")
    @Override
    public ResponseEntity<ProductVO> deleteProduct(@PathVariable String productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @GetMapping(value = "/{productId}/prices")
    @Override
    public ResponseEntity<PricingDetailsVO> getPrices(@PathVariable String productId) {
        return ResponseEntity.ok(productService.getPrices(productId));
    }

    private static URI location(String path, Object... args) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(path).buildAndExpand(args).toUri();
    }

    @Autowired
    private ProductService productService;
}
