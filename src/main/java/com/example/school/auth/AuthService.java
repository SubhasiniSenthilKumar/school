package com.example.school.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired AuthRep authRep;
    public Optional<Register> findByEmail(String email) {
        return authRep.findByEmail(email);
    }
    public void saves(Register reg) {
        authRep.save(reg);
    }
}
