package com.ship.communication.repository;

import com.ship.communication.model.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {
}
