package com.notesmanager.service;

import com.notesmanager.entity.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    Note createNote(Long topicId, Note note);

    List<Note> getNotesByTopic(Long topicId);

    Optional<Note> getNoteById(Long noteId);

    Note updateNote(Long noteId, Note note);

    void deleteNote(Long noteId);
}
