package com.castilloreyeskm.voglio.dto;

import java.util.List;

import com.castilloreyeskm.voglio.model.Category;

import lombok.Data;

@Data
public class VoglioDto {
	private Long id;
	private String name;
	private String description;
	private int priority;
	private int quantity;
	private boolean active;
	private Category category;
	private List<ImageDto> images;
}
