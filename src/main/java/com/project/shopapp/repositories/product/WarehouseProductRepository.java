package com.project.shopapp.repositories.product;

import com.project.shopapp.models.product.WarehouseProduct;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Long> {
    @Query("SELECT wp FROM WarehouseProduct wp " +
            "WHERE wp.product.id = :productId " +
            "AND wp.quantity > 0 " +
            "ORDER BY wp.quantity DESC")
    List<WarehouseProduct> findByProductIdAndQuantityGreaterThanOrderByQuantityDesc(
            @Param("productId") Long productId
    );

    List<WarehouseProduct> findByWarehouseId(Long warehouseId);

    @Query("SELECT wp FROM WarehouseProduct wp " +
            "JOIN wp.warehouse w " +
            "JOIN wp.product p " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR " +
            "w.name LIKE %:keyword% OR " +
            "w.location LIKE %:keyword% OR " +
            "p.name LIKE %:keyword%)")
    Page<WarehouseProduct> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}