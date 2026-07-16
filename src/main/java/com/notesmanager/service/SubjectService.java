package com.notesmanager.service;

import java.util.*;

import com.notesmanager.entity.Subject;

public interface SubjectService {

	Subject createSubject(Subject subject);

	List<Subject> getAllSubjects();
	
	Optional<Subject> getSubjectById(Long id);

	Optional<Subject> getSubjectByName(String name);
	
	Subject updateSubject(Long id, Subject subject);
	
	void deleteSubject(Long id);

	List<Subject> getSubjectsByCategory(Long categoryId);

	void assignCategoryToSubject(Long subjectId, Long categoryId);

	void removeSubjectFromCategory(Long subjectId);
}
