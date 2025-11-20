package com.example.school.jury;

import com.example.school.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Jury {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private boolean deleted =false;
    private String name;
    private String address;
    private String designation;
    private String password;
    private String confirmPassword;
    private String username;
    @ManyToOne
    private Role role;
}
