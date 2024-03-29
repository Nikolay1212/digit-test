package com.digit.test.repository;

import com.digit.test.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "select * from message order by id desc limit 1", nativeQuery = true)
    Optional<Message> getLastMessage();
}
