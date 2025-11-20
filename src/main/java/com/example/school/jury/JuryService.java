package com.example.school.jury;


import com.example.school.role.Role;
import com.example.school.role.RoleRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JuryService {
    @Autowired
    private JuryRepository juryRep;

    @Autowired
    private RoleRep roleRep;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Jury saveUser(RequestDto request) {

        Jury newUser = new Jury();

        newUser.setName(request.getName());
        newUser.setUsername(request.getUsername());

        // Set role
        if ("judge".equalsIgnoreCase(request.getRole())) {

            Role userRole = roleRep.findByName("JUDGE")
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            newUser.setRole(userRole);
        }

        newUser.setAddress(request.getAddress());
        newUser.setDesignation(request.getDesignation());


        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setConfirmPassword(request.getConfirmPassword());

        return juryRep.save(newUser);
    }

    public Jury updateUser(RequestDto request, Long id) {

        Jury user = juryRep.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(request.getUsername());
        user.setAddress(request.getAddress());
        user.setDesignation(request.getDesignation());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setConfirmPassword(request.getConfirmPassword());

        Role role = roleRep.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);

        return juryRep.save(user);
    }
    public List<Jury> getAllJury() {
        return juryRep.findByDeletedFalse(); // only accepted
    }
    public void deleteData(Long id) {
        Optional<Jury> optionalJury = juryRep.findById(id);


        if (optionalJury.isPresent()) {
            Jury jury = optionalJury.get();



            jury.setDeleted(true); // Soft delete
            juryRep.save(jury);
        } else {
            throw new RuntimeException("Judge not found with ID: " + id);
        }
    }

}
