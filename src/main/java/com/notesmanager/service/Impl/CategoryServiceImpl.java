package com.notesmanager.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.notesmanager.entity.Category;
import com.notesmanager.exception.CategoryNotFoundException;
import com.notesmanager.repository.CategoryRepository;
import com.notesmanager.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	@Transactional
	public Category createCategory(Category category) {
		log.info("Creating category: {}", category.getCategory());
		if (category.getCategory() == null || category.getCategory().isBlank()) {
			throw new IllegalArgumentException("Category name cannot be blank");
		}
		Category saved = categoryRepository.save(category);
		log.info("Category created with id: {}", saved.getId());
		return saved;
	}

	@Override
	public List<Category> getAllCategories() {
		log.info("Fetching all categories");
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(Long id) {
		log.info("Fetching category with id: {}", id);
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isEmpty()) {
			log.warn("Category not found with id: {}", id);
			throw new CategoryNotFoundException("Category not found with id: " + id);
		}
		return category.get();
	}

	@Override
	@Transactional
	public void deleteCategory(Long id) {
		log.info("Deleting category with id: {}", id);
		Category category = getCategoryById(id);
		categoryRepository.delete(category);
		log.info("Category deleted with id: {}", id);
	}

}
