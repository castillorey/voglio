package com.castilloreyeskm.voglio.service.category;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.castilloreyeskm.voglio.exceptions.AlreadyExistException;
import com.castilloreyeskm.voglio.exceptions.ResourceNotFoundException;
import com.castilloreyeskm.voglio.model.Category;
import com.castilloreyeskm.voglio.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
	private final CategoryRepository categoryRepository;

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
				.map(categoryRepository::save)
				.orElseThrow(() -> new AlreadyExistException(category.getName() + " already exists"));
	}

	@Override
	public Category updateCategory(Category category, Long categoryId) {
		return Optional.ofNullable(getCategoryById(categoryId)).map(oldCategory -> {
			oldCategory.setName(category.getName());
			return categoryRepository.save(oldCategory);
		}).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
	}

	@Override
	public void deleteCategoryById(Long id) {
		categoryRepository.findById(id)
				.ifPresentOrElse(categoryRepository::delete, () -> {
					throw new ResourceNotFoundException("Category not found!");
				});
	}

}
