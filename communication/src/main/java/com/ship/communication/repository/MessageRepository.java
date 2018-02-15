package com.ship.communication.repository;

import com.ship.communication.model.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    public Message findByTitle(@Param("title") String title);
}
