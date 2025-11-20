package com.example.school.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRep extends JpaRepository<Event, Long> {
    List<Event> findByDeletedFalse();
}
