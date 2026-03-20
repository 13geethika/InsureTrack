package com.insuretrack.notification.dto;

import com.insuretrack.common.enums.NotificationCategory;
import com.insuretrack.common.enums.NotificationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponseDTO {

    private Long notificationId;
    private Long userId;
    private String message;
    private NotificationCategory category;
    private NotificationStatus status;
    private LocalDateTime createdDate;
}
