package com.notesmanager.service;

import java.util.List;

import com.notesmanager.entity.Category;

public interface CategoryService {

	Category createCategory(Category category);

	List<Category> getAllCategories();

	Category getCategoryById(Long id);

	void deleteCategory(Long id);
}
