package com.Event.Utils;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Event {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int Id;   
	
	private String event_name	;
	private String  city_name	;
	private LocalDate date  ;
	private LocalTime time	 ;
	private float latitude	;
	private float longitude ;
}
