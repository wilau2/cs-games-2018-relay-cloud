package com.ship.communication.repository;

import com.ship.communication.model.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRequestRepository extends PagingAndSortingRepository<MessageRequest, Long> {
    public MessageRequest findByTitle(@Param("title") String title);
}
