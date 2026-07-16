package com.notesmanager.service.Impl;

import com.notesmanager.entity.Subject;
import com.notesmanager.entity.Topic;
import com.notesmanager.repository.SubjectRepository;
import com.notesmanager.repository.TopicRepository;
import com.notesmanager.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Topic createTopic(Long subjectId, Topic topic) {
        log.info("Creating topic for subject id {}", subjectId);
        if (topic.getTitle() == null || topic.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Topic title cannot be empty");
        }
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with id: " + subjectId));
        topic.setSubject(subject);
        Topic saved = topicRepository.save(topic);
        log.info("Topic created with id: {} for subject id {}", saved.getId(), subjectId);
        return saved;
    }

    @Override
    public List<Topic> getTopicsBySubject(Long subjectId) {
        log.info("Fetching topics for subject id {}", subjectId);
        return topicRepository.findBySubjectId(subjectId);
    }

    @Override
    public Topic updateTopic(Long topicId, Topic topic) {
        log.info("Updating topic with id: {}", topicId);
        Topic existing = topicRepository.findById(topicId)
                .orElseThrow(() -> {
                    log.warn("Topic not found with id: {}", topicId);
                    return new IllegalArgumentException("Topic not found with id: " + topicId);
                });
        if (topic.getTitle() == null || topic.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Topic title cannot be empty");
        }
        existing.setTitle(topic.getTitle().trim());
        existing.setDescription(topic.getDescription());
        existing.setYoutubeUrl(topic.getYoutubeUrl());
        Topic saved = topicRepository.save(existing);
        log.info("Topic updated successfully with id: {}", topicId);
        return saved;
    }
}
