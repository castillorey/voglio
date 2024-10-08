package com.castilloreyeskm.voglio.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Voglio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private int priority;
	private int quantity;
	private boolean active;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

	@OneToMany(mappedBy = "voglio", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images;

	public Voglio(String name, String description, int priority, int quantity, boolean active, Category category) {
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.quantity = quantity;
		this.active = active;
		this.category = category;
	}
}
