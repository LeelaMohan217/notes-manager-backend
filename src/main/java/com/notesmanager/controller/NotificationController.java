package com.notesmanager.controller;

import com.notesmanager.entity.Notification;
import com.notesmanager.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@Slf4j
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        log.info("Creating notification: {}", notification.getMessage());
        Notification saved = notificationService.createNotification(notification);
        log.info("Notification created with id: {}", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Notification>> getActiveNotifications() {
        log.info("Fetching active notifications");
        return ResponseEntity.ok(notificationService.getActiveNotifications());
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        log.info("Fetching all notifications");
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.info("Deleting notification with id: {}", id);
        notificationService.deleteNotification(id);
        log.info("Notification deleted successfully with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
