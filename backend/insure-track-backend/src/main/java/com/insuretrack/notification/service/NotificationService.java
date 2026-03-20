package com.insuretrack.notification.service;

import com.insuretrack.common.enums.NotificationCategory;
import com.insuretrack.notification.dto.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(
            Long userId,
            String message,
            NotificationCategory category);

    List<NotificationResponseDTO> getUserNotifications(
            Long userId);

    NotificationResponseDTO markAsRead(Long notificationId);

    NotificationResponseDTO dismissNotification(Long notificationId);
}
