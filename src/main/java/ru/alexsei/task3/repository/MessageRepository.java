package ru.alexsei.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.alexsei.task3.model.Message;

public interface MessageRepository extends JpaRepository<Message,Long> {
    @Query(value = "SELECT nextval('hibernate_sequence') as num" , nativeQuery = true)
    Long getNextSeriesId();

    @Modifying
    @Transactional
    @Query(value = "UPDATE Message m set m.isRead=true where m.messageId=:id")
    void readMessage(@Param("id") Long id);

}
