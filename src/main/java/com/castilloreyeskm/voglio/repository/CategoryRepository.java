package com.castilloreyeskm.voglio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.castilloreyeskm.voglio.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String name);
	boolean existsByName(String name);
}
