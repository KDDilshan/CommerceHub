package com.kavindu.commercehub.Product.Repositories;

import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.Product.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(UUID id);

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    List<Product> searchByDescription(@Param("description") String description);


    // Method to find all products sorted by price in ascending order
    List<Product> findAllByOrderByPriceAsc();

    // Method to find all products sorted by price in descending order
    List<Product> findAllByOrderByPriceDesc();

    List<Product> findAll(Sort sort);

    List<Product> findByCreatedBy(AppUser user);

}
