package com.prizy.product.domain.repository;

import com.prizy.product.domain.entity.PricingDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PricingDetailsRepository extends CrudRepository<PricingDetails, String> {

    @Query("SELECT p FROM PricingDetails p WHERE p.product = :productId AND p.created = (" +
            "SELECT MAX(p1.created) FROM PricingDetails p1 WHERE product = :productId)")
    PricingDetails findLatestByProductId(@Param("productId") String productId);
}
