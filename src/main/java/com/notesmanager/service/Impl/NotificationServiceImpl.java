package com.notesmanager.service.Impl;

import com.notesmanager.entity.Notification;
import com.notesmanager.repository.NotificationRepository;
import com.notesmanager.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        log.info("Creating notification");
        if (notification.getMessage() == null || notification.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Notification message cannot be empty");
        }
        Notification saved = notificationRepository.save(notification);
        log.info("Notification created with id: {}", saved.getId());
        return saved;
    }

    @Override
    public List<Notification> getActiveNotifications() {
        log.info("Fetching active notifications");
        return notificationRepository.findByActiveTrueOrderByCreatedAtDesc();
    }

    @Override
    public List<Notification> getAllNotifications() {
        log.info("Fetching all notifications");
        return notificationRepository.findAll();
    }

    @Override
    public void deleteNotification(Long id) {
        log.info("Deleting notification with id: {}", id);
        notificationRepository.deleteById(id);
        log.info("Notification deleted successfully with id: {}", id);
    }
}
