package com.example.school.participants;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String participantName;

    private LocalDate dateOfBirth;
    private Long age;
    private String gender;
    private String category;
    private String schoolName;
    private String standard;
    private String yogaMasterName;
    private Long yogaMasterContact;
    private String address;

    private String photo; // to store image filename

}
