package com.notesmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notesmanager.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findBySubjectIgnoreCase(String subject);

    List<Subject> findByCategoryId(Long categoryId);

}
