package com.insuretrack.notification.controller;

import com.insuretrack.common.enums.NotificationCategory;
import com.insuretrack.notification.dto.NotificationResponseDTO;
import com.insuretrack.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/users/{userId}")
    public NotificationResponseDTO createNotification(
            @PathVariable Long userId,
            @RequestParam String message,
            @RequestParam NotificationCategory category) {

        return notificationService
                .createNotification(userId, message, category);
    }

    @GetMapping("/users/{userId}")
    public List<NotificationResponseDTO> getUserNotifications(
            @PathVariable Long userId) {

        return notificationService
                .getUserNotifications(userId);
    }

    @PutMapping("/{id}/read")
    public NotificationResponseDTO markAsRead(
            @PathVariable Long id) {

        return notificationService.markAsRead(id);
    }

    @PutMapping("/{id}/dismiss")
    public NotificationResponseDTO dismissNotification(
            @PathVariable Long id) {

        return notificationService.dismissNotification(id);
    }
}