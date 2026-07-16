package com.notesmanager.service.Impl;

import com.notesmanager.entity.Note;
import com.notesmanager.entity.Topic;
import com.notesmanager.repository.NoteRepository;
import com.notesmanager.repository.TopicRepository;
import com.notesmanager.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Note createNote(Long topicId, Note note) {
        log.info("Creating note for topic id {}", topicId);
        if (note.getTitle() == null || note.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Note title cannot be empty");
        }
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> {
                    log.warn("Topic not found with id: {}", topicId);
                    return new IllegalArgumentException("Topic not found with id: " + topicId);
                });
        note.setTopic(topic);
        Note saved = noteRepository.save(note);
        log.info("Note created with id: {} for topic id {}", saved.getId(), topicId);
        return saved;
    }

    @Override
    public List<Note> getNotesByTopic(Long topicId) {
        log.info("Fetching notes for topic id {}", topicId);
        return noteRepository.findByTopicIdOrderByCreatedAtAsc(topicId);
    }

    @Override
    public Optional<Note> getNoteById(Long noteId) {
        log.info("Fetching note with id: {}", noteId);
        Optional<Note> note = noteRepository.findById(noteId);
        if (note.isEmpty()) {
            log.warn("Note not found with id: {}", noteId);
        }
        return note;
    }

    @Override
    public Note updateNote(Long noteId, Note note) {
        log.info("Updating note with id: {}", noteId);
        Note existing = noteRepository.findById(noteId)
                .orElseThrow(() -> {
                    log.warn("Note not found with id: {}", noteId);
                    return new IllegalArgumentException("Note not found with id: " + noteId);
                });
        if (note.getTitle() == null || note.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Note title cannot be empty");
        }
        existing.setTitle(note.getTitle().trim());
        existing.setContent(note.getContent());
        Note saved = noteRepository.save(existing);
        log.info("Note updated successfully with id: {}", noteId);
        return saved;
    }

    @Override
    public void deleteNote(Long noteId) {
        log.info("Deleting note with id: {}", noteId);
        noteRepository.deleteById(noteId);
        log.info("Note deleted successfully with id: {}", noteId);
    }
}
