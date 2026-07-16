package com.notesmanager.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.notesmanager.entity.Category;
import com.notesmanager.entity.Subject;
import com.notesmanager.exception.CategoryNotFoundException;
import com.notesmanager.exception.SubjectNotFoundException;
import com.notesmanager.repository.CategoryRepository;
import com.notesmanager.repository.SubjectRepository;
import com.notesmanager.service.SubjectService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Subject createSubject(Subject subject) {
		log.info("Creating subject: {}", subject.getSubject());
		if (subject.getSubject() == null || subject.getSubject().trim().isEmpty()) {
			throw new IllegalArgumentException("Subject cannot be empty");
		}
		Subject saved = subjectRepository.save(subject);
		log.info("Subject created with id: {}", saved.getId());
		return saved;
	}

	@Override
	public List<Subject> getAllSubjects() {
		log.info("Fetching all subjects");
		return subjectRepository.findAll();
	}

	@Override
	public Optional<Subject> getSubjectById(Long id) {
		log.info("Fetching subject with id: {}", id);
		Optional<Subject> subject = subjectRepository.findById(id);
		if (subject.isEmpty()) {
			log.warn("Subject not found with id: {}", id);
		}
		return subject;
	}

	@Override
	public Optional<Subject> getSubjectByName(String name) {
		log.info("Fetching subject by name: {}", name);
		// name param is a URL slug (e.g. "spring-boot"), convert back to
		// space-separated for lookup
		String normalized = name.replace("-", " ");
		return subjectRepository.findBySubjectIgnoreCase(normalized);
	}

	@Override
	@Transactional
	public Subject updateSubject(Long id, Subject subject) {
		log.info("Updating subject with id: {}", id);

		Optional<Subject> checkSubject = subjectRepository.findById(id);

		if (checkSubject.isEmpty()) {
			log.warn("Subject not found with id: {}", id);
			throw new SubjectNotFoundException("Subject not found with id: " + id);
		}

		log.info("Subject found : {} ", checkSubject);

		Subject existingSubject = checkSubject.get();
		existingSubject.setSubject(subject.getSubject());

		Subject updatedSubject = subjectRepository.save(existingSubject);
		log.info("Subject updated successfully with id: {}", id);
		return updatedSubject;
	}

	@Override
	@Transactional
	public void deleteSubject(Long id) {

		log.info("Deleting subject with id: {}", id);

		Optional<Subject> optionalSubject = subjectRepository.findById(id);

		if (optionalSubject.isEmpty()) {

			log.warn("Subject not found with id: {}", id);

			throw new SubjectNotFoundException("Subject not found with id: " + id);
		}

		Subject existingSubject = optionalSubject.get();

		log.info("Subject found: {}", existingSubject);

		subjectRepository.delete(existingSubject);

		log.info("Subject deleted successfully with id: {}", id);
	}

	@Override
	public List<Subject> getSubjectsByCategory(Long categoryId) {
		log.info("Fetching subjects for category id: {}", categoryId);
		return subjectRepository.findByCategoryId(categoryId);
	}

	@Override
	@Transactional
	public void assignCategoryToSubject(Long subjectId, Long categoryId) {
		log.info("Assigning category {} to subject {}", categoryId, subjectId);
		Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
		if (optionalSubject.isEmpty()) {
			log.warn("Subject not found with id: {}", subjectId);
			throw new SubjectNotFoundException("Subject not found with id: " + subjectId);
		}
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		if (optionalCategory.isEmpty()) {
			log.warn("Category not found with id: {}", categoryId);
			throw new CategoryNotFoundException("Category not found with id: " + categoryId);
		}
		Subject subject = optionalSubject.get();
		subject.setCategory(optionalCategory.get());
		subjectRepository.save(subject);
		log.info("Category {} assigned to subject {}", categoryId, subjectId);
	}

	@Override
	@Transactional
	public void removeSubjectFromCategory(Long subjectId) {
		log.info("Removing category from subject {}", subjectId);
		Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
		if (optionalSubject.isEmpty()) {
			log.warn("Subject not found with id: {}", subjectId);
			throw new SubjectNotFoundException("Subject not found with id: " + subjectId);
		}
		Subject subject = optionalSubject.get();
		subject.setCategory(null);
		subjectRepository.save(subject);
		log.info("Category removed from subject {}", subjectId);
	}

}
