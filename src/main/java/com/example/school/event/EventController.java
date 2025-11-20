package com.example.school.event;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("event")
public class EventController {
   @Autowired
   private EventService eventService;
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> submitData(
            @RequestPart("data") String data, // JSON string
            @RequestPart(value = "photo", required = false) MultipartFile photo) {

        Map<String, Object> response = new HashMap<>();
        try {
            // Convert JSON string to DTO
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            RequestDto requestDto = mapper.readValue(data, RequestDto.class);

            // Save participant
            Event newUser = eventService.save(requestDto, photo);

            // Build user map
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", newUser.getId());
            userMap.put("title", newUser.getTitle());
            userMap.put("description", newUser.getDescription());
            userMap.put("venue", newUser.getVenue());
            userMap.put("venue_address", newUser.getVenueAddress());
            userMap.put("start_date", newUser.getStartDate());
            userMap.put("end_date", newUser.getEndDate());



            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("user", userMap);

            // Final response
            response.put("status", "success");
            response.put("message", "Data saved successfully!");
            response.put("data", dataMap);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error saving data!");
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,Object>> updateItem(
            @PathVariable Long id,
            @RequestPart("data") String data,
            @RequestPart(value = "photo", required = false) MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Convert JSON string to DTO
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            RequestDto requestDto = mapper.readValue(data, RequestDto.class);

            // Save participant
            Event newUser = eventService.updateItems(requestDto, file, id);

            // Build user map
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", newUser.getId());
            userMap.put("title", newUser.getTitle());
            userMap.put("description", newUser.getDescription());
            userMap.put("venue", newUser.getVenue());
            userMap.put("venue_address", newUser.getVenueAddress());
            userMap.put("start_date", newUser.getStartDate());
            userMap.put("end_date", newUser.getEndDate());



            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("user", userMap);

            // Final response
            response.put("status", "success");
            response.put("message", "Data updated successfully!");
            response.put("data", dataMap);

            return ResponseEntity.ok(response);

            //return eventService.updateItems(itemJson, file, id);


        } catch (Exception e) {

            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error saving data!");
            return ResponseEntity.badRequest().body(response);


        }

    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllEvents() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Event> events = eventService.getAllEvents();

            List<Map<String, Object>> users = new ArrayList<>();
            for (Event e : events) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", e.getId());
                userMap.put("title", e.getTitle());
                userMap.put("description", e.getDescription());
                userMap.put("venue", e.getVenue());
                userMap.put("venue_address", e.getVenueAddress());
                userMap.put("start_date", e.getStartDate());
                userMap.put("end_date", e.getEndDate());

                userMap.put("photo", e.getFileName());


                users.add(userMap);
            }

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("users", users);

            response.put("status", "success");
            response.put("message", "Events retrieved successfully!");
            response.put("data", dataMap);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error retrieving events!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEvent(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {


            eventService.deleteData(id);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("users", "");

            response.put("status", "success");
            response.put("message", "Event deleted successfully!");
            response.put("data", dataMap);


            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error in deleting data!");
            return ResponseEntity.badRequest().body(response);
        }
    }


}
