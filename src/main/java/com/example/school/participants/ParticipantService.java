package com.example.school.participants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ParticipantService {

@Autowired
private ParticipantRep participantRep;
    public ResponseEntity<Map<String, String>> save(String data, MultipartFile photo)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper(); // it from jackson library in spirng boot to handle jso
        // Json to javaObject
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Participants participants = mapper.readValue(data, Participants.class);
        // ✅ Handle file saving (optional)
        if (photo != null && !photo.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads";
            File directory = new File(uploadDir);
            if (!directory.exists())
                directory.mkdirs();

            String fileName = photo.getOriginalFilename();

            String filePath = uploadDir + "/" + fileName; // fixed path handling
            photo.transferTo(new File(filePath));
            participants.setPhoto(fileName); // ✅ Save filename if file exists
        } else {
            participants.setPhoto(null); // ✅ Explicitly handle no file case
        }
        // ✅ Save the item to DB (ensure filename can be null in your entity)
        participantRep.save(participants);

        return ResponseEntity.ok(Collections.singletonMap("message", "Data saved successfully!"));

    }

}
