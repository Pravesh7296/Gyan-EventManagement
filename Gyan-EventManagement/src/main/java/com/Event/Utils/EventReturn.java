package com.Event.Utils;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EventReturn {
	
	private String event_name	;
	private String  city_name	;
	private LocalDate date  ;
	private String weather;
	private double distance_km ;
	
}
