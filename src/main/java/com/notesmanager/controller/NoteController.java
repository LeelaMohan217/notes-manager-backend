package com.notesmanager.controller;

import com.notesmanager.entity.Note;
import com.notesmanager.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topic/{topicId}/notes")
@Slf4j
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> createNote(@PathVariable Long topicId, @RequestBody Note note) {
        log.info("Creating note for topic id {}", topicId);
        Note saved = noteService.createNote(topicId, note);
        log.info("Note created with id: {} for topic id {}", saved.getId(), topicId);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotes(@PathVariable Long topicId) {
        log.info("Fetching notes for topic id {}", topicId);
        return ResponseEntity.ok(noteService.getNotesByTopic(topicId));
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<Note> getNote(@PathVariable Long topicId, @PathVariable Long noteId) {
        log.info("Fetching note with id: {} for topic id {}", noteId, topicId);
        return noteService.getNoteById(noteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<Note> updateNote(
            @PathVariable Long topicId,
            @PathVariable Long noteId,
            @RequestBody Note note) {
        log.info("Updating note id {} for topic id {}", noteId, topicId);
        Note updated = noteService.updateNote(noteId, note);
        log.info("Note updated successfully with id: {}", noteId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long topicId, @PathVariable Long noteId) {
        log.info("Deleting note id {} for topic id {}", noteId, topicId);
        noteService.deleteNote(noteId);
        log.info("Note deleted successfully with id: {}", noteId);
        return ResponseEntity.noContent().build();
    }
}
