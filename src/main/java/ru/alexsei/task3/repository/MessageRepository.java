package ru.alexsei.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.alexsei.task3.model.Message;

public interface MessageRepository extends JpaRepository<Message,Long> {
    @Query(value = "SELECT nextval('hibernate_sequence') as num" , nativeQuery = true)
    Long getNextSeriesId();
}
