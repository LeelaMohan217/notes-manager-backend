package com.notesmanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notesmanager.entity.Subject;
import com.notesmanager.service.SubjectService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/subject")
@Slf4j
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@PostMapping
	public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {

		log.info("Creating subject : {}", subject.getSubject());
		Subject savedSubject = subjectService.createSubject(subject);
		log.info("{} subject created succesfully", savedSubject.getSubject());
		return ResponseEntity.status(HttpStatus.CREATED).body(savedSubject);

	}

	@GetMapping
	public ResponseEntity<List<Subject>> getAllSubjects() {

		log.info("Fetching all subjects.");
		List<Subject> subjects = subjectService.getAllSubjects();
		log.info("Fetched subjects: {}", subjects);
		return ResponseEntity.ok(subjects);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
		log.info("Fetching subject with id: {}", id);
		Optional<Subject> subject = subjectService.getSubjectById(id);
		if (subject.isPresent()) {
			return ResponseEntity.ok(subject.get());
		}
		return ResponseEntity.notFound().build();

	}

	@GetMapping("/name/{name}")
	public ResponseEntity<Subject> getSubjectByName(@PathVariable String name) {
		log.info("Fetch subject by name: {}", name);
		Optional<Subject> subject = subjectService.getSubjectByName(name);
		if (subject.isPresent()) {
			return ResponseEntity.ok(subject.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject subject) {
		log.info("Updating subject with id: {}", id);
		Subject updatedSubject = subjectService.updateSubject(id, subject);
		log.info("Subject updated successfully with id: {}", id);
		return ResponseEntity.ok(updatedSubject);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {

		log.info("Received request to delete subject with id: {}", id);

		subjectService.deleteSubject(id);

		log.info("Subject deleted successfully with id: {}", id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Subject>> getSubjectsByCategory(@PathVariable Long categoryId) {
		return ResponseEntity.ok(subjectService.getSubjectsByCategory(categoryId));
	}

	@PutMapping("/{id}/category/{categoryId}")
	public ResponseEntity<Subject> assignCategory(@PathVariable Long id, @PathVariable Long categoryId) {
		subjectService.assignCategoryToSubject(id, categoryId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}/category")
	public ResponseEntity<Void> removeCategory(@PathVariable Long id) {
		subjectService.removeSubjectFromCategory(id);
		return ResponseEntity.noContent().build();
	}

}
