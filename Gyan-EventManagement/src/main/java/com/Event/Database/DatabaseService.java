package com.Event.Database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.Event.CsvOperation.CsvReader;
import com.Event.Utils.Event;
import com.Event.Utils.EventReturn;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DatabaseService {
   @Autowired
   private DatabaseRepo dbr;
   
   public void SaveAllEventToDatabse(){
	   CsvReader reader = new CsvReader();
		List<Event> list = new ArrayList();
	      List<CSVRecord> records = reader.readCsvFile("CSV_file.csv");
	      for (CSVRecord record : records) {
	    	  Event event = new Event();
	   
	        event.setEvent_name(record.get("event_name"));
	        event.setCity_name(record.get("city_name"));
	        
	        String timeString  = record.get("time");
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss"); // Specify the correct pattern
	        LocalTime time = LocalTime.parse(timeString, formatter);
	        event.setTime(time);
	        
	        
	        event.setDate(LocalDate.parse(record.get("date")));
	        event.setLatitude(Float.parseFloat( record.get("latitude")));
	        event.setLongitude(Float.parseFloat(record.get("longitude")));
	        list.add(event);
	      }
	      if(dbr!=null) {
	    	  dbr.saveAll(list);
	      }else {
	    	  System.out.println("dbr Still Null");
	      }
   }
   
   public List<EventReturn> getEvent(float latitude1,float longitude1,LocalDate date){
	    List<Event> list = dbr.findAll(Sort.by(Sort.Direction.ASC,"date"));
	   List<Event>  fl = list.stream().filter(p->p.getDate().isBefore(date.plusDays(15)) && p.getDate().isAfter(date.minusDays(1))).toList();
	  
	   List <EventReturn> evrList = new ArrayList();
	   
	   for(Event e : fl) {
		   
		   EventReturn evr = new EventReturn(); 
		   evr.setEvent_name(e.getEvent_name());
		   evr.setCity_name(e.getCity_name());
		   evr.setDate(e.getDate());
		   evr.setWeather(this.getWeather(e.getCity_name(), date));
		   evr.setDistance_km(this.getDistance(latitude1,e.getLatitude(), longitude1, e.getLongitude()));
		   evrList.add(evr);
		   
	   }
	   
	    
	   return evrList;
   }
   
   public String getWeather(String city,LocalDate date) {
	   RestTemplate rt = new RestTemplate();
	   ObjectMapper om = new ObjectMapper();
	   String weather = "Weather API created Issue";
	   String apiUrl = "https://gg-backend-assignment.azurewebsites.net/api/Weather?code=Kf" +
			   "QnTWHJbg1giyB_Q9Ih3Xu3L9QOBDTuU5zwqVikZepCAzFut3rqsg==&city="
			   + city
			   + "%20Rebeccaberg&date="
			   + date;
	   
	   
	   try {
		JsonNode jn = om.readTree(rt.getForObject(apiUrl,String.class));
		 weather = jn.get("weather").asText();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return weather ;
	   
   }
public double getDistance(float latitude1,float latitude2,float longitude1,float longitude2) {
	   
   RestTemplate rt = new RestTemplate();
   ObjectMapper om = new ObjectMapper();
   double distance = 0;
	   String apiUrl = "https://gg-backend-assignment.azurewebsites.net/api/Distance?code=IAKvV2EvJa6Z6dEIUqqd7yGAu7IZ8gaH-a0QO6btjRc1AzFu8Y3IcQ==&latitude1="
			  + latitude1 +
			  "&longitude1="
			  +longitude1 +
			  "&latitude2=" 
			  + latitude2+
			  "&longitude2="  
			+  longitude2 ;
	try {
		JsonNode jn = om.readTree(rt.getForObject(apiUrl, String.class));
		distance = jn.get("distance").asDouble();
	} catch (JsonProcessingException | RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return distance;
	
   }
   
}
