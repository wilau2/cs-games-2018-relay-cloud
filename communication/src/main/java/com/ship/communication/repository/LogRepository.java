package main.java.com.ship.communication.repository;

public interface LogRepository extends PagingAndSortingRepository<Message, Long> {
    public Message findByType(@Param("type") String title);
}