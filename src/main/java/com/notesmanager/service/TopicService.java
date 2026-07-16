package com.notesmanager.service;

import com.notesmanager.entity.Topic;

import java.util.List;

public interface TopicService {

    Topic createTopic(Long subjectId, Topic topic);

    List<Topic> getTopicsBySubject(Long subjectId);

    Topic updateTopic(Long topicId, Topic topic);
}
