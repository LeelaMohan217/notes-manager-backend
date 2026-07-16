package com.notesmanager.service;

import com.notesmanager.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(Notification notification);

    List<Notification> getActiveNotifications();

    List<Notification> getAllNotifications();

    void deleteNotification(Long id);
}
