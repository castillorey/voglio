package com.castilloreyeskm.voglio.repository;

import com.castilloreyeskm.voglio.model.Image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
	List<Image> findByVoglioId(Long id);
}
