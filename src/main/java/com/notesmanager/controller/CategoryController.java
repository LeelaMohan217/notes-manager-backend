package com.notesmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notesmanager.entity.Category;
import com.notesmanager.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		log.info("Creating category: {}", category.getCategory());
		Category createdCategory = categoryService.createCategory(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
	}

	@GetMapping
	public ResponseEntity<List<Category>> getAllCategories() {
		log.info("Fetching all categories");
		List<Category> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
		log.info("Fetching category with id: {}", id);
		Category category = categoryService.getCategoryById(id);
		return ResponseEntity.ok(category);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		log.info("Deleting category with id: {}", id);
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}
}
