package com.castilloreyeskm.voglio.request;

import com.castilloreyeskm.voglio.model.Category;

import lombok.Data;

@Data
public class VoglioUpdateRequest {
	private Long id;
	private String name;
	private String description;
	private int priority;
	private int quantity;
	private boolean active;
	private Category category;
}
