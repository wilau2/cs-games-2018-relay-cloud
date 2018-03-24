package com.ship.communication.repository;

import com.ship.communication.model.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public class MessageLog implements MessageRepository {
    ArrayList<Message> messages;
    public Message findByTitle(@Param("title") String title){

    }

    public Message save(message) {
      messages.add(message);
    }
}
