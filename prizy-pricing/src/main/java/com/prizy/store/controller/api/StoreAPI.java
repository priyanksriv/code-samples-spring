package com.prizy.store.controller.api;

import com.prizy.store.vo.StorePriceVO;
import com.prizy.store.vo.StoreVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(value = "Store", produces =  "application/json")
public interface StoreAPI {

    @ApiOperation(value = "Get a list of stores", notes = "Returns a list of stores.",
            response = List.class, tags = { "store" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Error handling request") })
    ResponseEntity<List<StoreVO>> findAllStores();


    @ApiOperation(value = "Create a store.", notes = "Creates a store.",
            response = Void.class, tags = { "store" }, consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Store created", response = Void.class),
            @ApiResponse(code = 500, message = "Error handling request", response = Error.class) })
    ResponseEntity<Void> createStore(StoreVO storeVO);


    @ApiOperation(value = "Find a store.", notes = "Finds a store.",
            response = StoreVO.class , tags = { "store" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = StoreVO.class),
            @ApiResponse(code = 404, message = "Store not found", response = Error.class),
            @ApiResponse(code = 500, message = "Error handling request", response = Error.class) })
    ResponseEntity<StoreVO> findStore(String storeId);


    @ApiOperation(value = "Update a store.", notes = "Updates a store.",
            response = Void.class , tags = { "store" }, consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Store updated", response = Void.class),
            @ApiResponse(code = 404, message = "Store not found", response = Error.class),
            @ApiResponse(code = 500, message = "Error handling request", response = Error.class) })
    ResponseEntity<Void> updateStore(String storeId, StoreVO storeVO);


    @ApiOperation(value = "Delete a store.", notes = "Deletes a store.",
            response = StoreVO.class , tags = { "store" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Store deleted", response = StoreVO.class),
            @ApiResponse(code = 404, message = "Store not found", response = Error.class),
            @ApiResponse(code = 500, message = "Error handling request", response = Error.class) })
    ResponseEntity<StoreVO> deleteStore(String storeId);


    @ApiOperation(value = "Add new store price for a product.", notes = "Adds new store price for a product.",
            response = StorePriceVO.class, tags = { "store-price" }, consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Store price added", response = StorePriceVO.class),
            @ApiResponse(code = 500, message = "Error handling request", response = Error.class) })
    ResponseEntity<StorePriceVO> saveStorePrice(StorePriceVO storePriceVO);
}
