package com.notesmanager.controller;

import com.notesmanager.entity.Topic;
import com.notesmanager.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject/{subjectId}/topics")
@Slf4j
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<Topic> createTopic(
            @PathVariable Long subjectId,
            @RequestBody Topic topic) {
        log.info("Creating topic for subject id {}", subjectId);
        Topic saved = topicService.createTopic(subjectId, topic);
        log.info("Topic created with id: {} for subject id {}", saved.getId(), subjectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Topic>> getTopics(@PathVariable Long subjectId) {
        log.info("Fetching topics for subject id {}", subjectId);
        return ResponseEntity.ok(topicService.getTopicsBySubject(subjectId));
    }

    @PutMapping("/{topicId}")
    public ResponseEntity<Topic> updateTopic(
            @PathVariable Long subjectId,
            @PathVariable Long topicId,
            @RequestBody Topic topic) {
        log.info("Updating topic id {} for subject id {}", topicId, subjectId);
        Topic updated = topicService.updateTopic(topicId, topic);
        log.info("Topic updated successfully with id: {}", topicId);
        return ResponseEntity.ok(updated);
    }
}
