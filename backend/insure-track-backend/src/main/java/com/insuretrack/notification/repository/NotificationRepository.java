package com.insuretrack.notification.repository;

import com.insuretrack.common.enums.NotificationStatus;
import com.insuretrack.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByUser_UserId(Long userId);

    List<Notification> findByUser_UserIdAndStatus(
            Long userId,
            NotificationStatus status);
}
