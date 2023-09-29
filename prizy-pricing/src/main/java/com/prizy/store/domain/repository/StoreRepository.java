package com.prizy.store.domain.repository;

import com.prizy.store.domain.entity.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StoreRepository extends CrudRepository<Store, String> {
}
