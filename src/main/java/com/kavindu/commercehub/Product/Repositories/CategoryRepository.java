package com.kavindu.commercehub.Product.Repositories;

import com.kavindu.commercehub.Product.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
