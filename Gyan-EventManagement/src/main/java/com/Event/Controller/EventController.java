package com.Event.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Event.Database.DatabaseService;
import com.Event.Utils.EventReturn;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private DatabaseService dbs;

    @GetMapping("AddAllEvent")
    public ResponseEntity<String> AddAllEvent(){
        dbs.SaveAllEventToDatabse();
        return ResponseEntity.ok("All Event Added Successfully");
    }
    
    @GetMapping("/find")
    public ResponseEntity<List<EventReturn>> getAllEvent(@RequestParam float latitude,@RequestParam float longitude,@RequestParam LocalDate date){
        
        
        return ResponseEntity.ok(dbs.getEvent(latitude, longitude, date));
    }
	
	
}
