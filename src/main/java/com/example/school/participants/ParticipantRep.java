package com.example.school.participants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRep extends JpaRepository<Participants,Long> {
}
