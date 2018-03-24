package com.ship.communication.repository;

import com.ship.communication.model.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface LoggingRepositoryRepository extends PagingAndSortingRepository<String, Long> {
}
