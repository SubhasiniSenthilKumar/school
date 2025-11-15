package com.example.school.participants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/participants")
public class ParticipantController {


    @Autowired
    private ParticipantService participantService;
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> submitData(@RequestPart("data") String data,

                                                          @RequestPart(value = "photo", required = false) MultipartFile photo) {

        try {
            participantService.save(data, photo);
            return ResponseEntity.ok(Collections.singletonMap("message", "Data saved successfully!"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Error saving data!"));
        }

    }
}
