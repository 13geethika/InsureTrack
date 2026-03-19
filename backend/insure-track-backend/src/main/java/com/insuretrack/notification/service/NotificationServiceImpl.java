package com.insuretrack.notification.service;

import com.insuretrack.common.enums.NotificationCategory;
import com.insuretrack.common.enums.NotificationStatus;
import com.insuretrack.notification.dto.NotificationResponseDTO;
import com.insuretrack.notification.entity.Notification;
import com.insuretrack.notification.repository.NotificationRepository;
import com.insuretrack.user.entity.User;
import com.insuretrack.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public NotificationResponseDTO createNotification(
            Long userId,
            String message,
            NotificationCategory category) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Notification notification = Notification.builder()
                .user(user)
                .message(message)
                .category(category)
                .status(NotificationStatus.UNREAD)
                .createdDate(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        return mapToResponse(notification);
    }

    @Override
    public List<NotificationResponseDTO> getUserNotifications(
            Long userId) {

        return notificationRepository
                .findByUser_UserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public NotificationResponseDTO markAsRead(Long notificationId) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException("Notification not found"));

        notification.setStatus(NotificationStatus.READ);

        return mapToResponse(notification);
    }

    @Override
    public NotificationResponseDTO dismissNotification(
            Long notificationId) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException("Notification not found"));

        notification.setStatus(NotificationStatus.DISMISSED);

        return mapToResponse(notification);
    }

    private NotificationResponseDTO mapToResponse(
            Notification notification) {

        return NotificationResponseDTO.builder()
                .notificationId(notification.getNotificationId())
                .userId(notification.getUser().getUserId())
                .message(notification.getMessage())
                .category(notification.getCategory())
                .status(notification.getStatus())
                .createdDate(notification.getCreatedDate())
                .build();
    }
}
