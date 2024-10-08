package com.castilloreyeskm.voglio.service.category;

import java.util.List;

import com.castilloreyeskm.voglio.model.Category;

public interface ICategoryService {
	Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long categoryId);
    void deleteCategoryById(Long id);
}
