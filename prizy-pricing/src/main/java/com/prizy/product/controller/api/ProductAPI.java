package com.prizy.product.controller.api;

import com.prizy.product.vo.PricingDetailsVO;
import com.prizy.product.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(value = "Product", produces =  "application/json")
public interface ProductAPI {

    @ApiOperation(value = "Get a list of products", notes = "Returns a list of products.",
            response = List.class, tags = { "product" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Error handling request") })
    ResponseEntity<List<ProductVO>> findAllProducts();


    @ApiOperation(value = "Create a product.", notes = "Creates a product.",
            response = Void.class, tags = { "product" }, consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product created", response = Void.class),
            @ApiResponse(code = 500, message = "Error handling request", response = Error.class) })
    ResponseEntity<Void> createProduct(ProductVO productVO);


    @ApiOperation(value = "Find a product.", notes = "Finds a product.",
            response = ProductVO.class , tags = { "product" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ProductVO.class),
            @ApiResponse(code = 404, message = "Product not found", response = Error.class),
            @ApiResponse(code = 500, message = "Error handling request", response = Error.class) })
    ResponseEntity<ProductVO> findProduct(String productId);


    @ApiOperation(value = "Update a product.", notes = "Updates a product.",
            response = Void.class , tags = { "product" }, consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Product updated", response = Void.class),
            @ApiResponse(code = 404, message = "Product not found", response = Error.class),
            @ApiResponse(code = 500, message = "Error handling request", response = Error.class) })
    ResponseEntity<Void> updateProduct(String productId, ProductVO productVO);


    @ApiOperation(value = "Delete a product.", notes = "Deletes a product.",
            response = ProductVO.class , tags = { "product" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product deleted", response = ProductVO.class),
            @ApiResponse(code = 404, message = "Product not found", response = Error.class),
            @ApiResponse(code = 500, message = "Error handling request", response = Error.class) })
    ResponseEntity<ProductVO> deleteProduct(String productId);


    @ApiOperation(value = "Get prices for a product.", notes = "Gets prices for a product.",
            response = PricingDetailsVO.class , tags = { "pricing" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PricingDetailsVO.class),
            @ApiResponse(code = 404, message = "Product not found", response = Error.class),
            @ApiResponse(code = 500, message = "Error handling request", response = Error.class) })
    ResponseEntity<PricingDetailsVO> getPrices(String productId);
}
