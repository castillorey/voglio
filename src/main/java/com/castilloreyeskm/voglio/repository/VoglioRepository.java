package com.castilloreyeskm.voglio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.castilloreyeskm.voglio.model.Voglio;

public interface VoglioRepository extends JpaRepository<Voglio, Long> {

	List<Voglio> findByCategoryName(String categoryName);

	List<Voglio> findByName(String voglioName);

}
