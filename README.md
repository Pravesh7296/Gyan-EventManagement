# Gyan-EventManagement

# API Documentation:
First of all use "http://localhost:8081/swagger-ui/index.html#/event-controller/AddAllEvent" or "http://localhost:8081/event/AddAllEvent" for uploding all event to Database (use only get request)
REST API endpoint to list all events based on the
user's latitude, longitude, and a specified date. The system must return
events occurring within the next 14 days from the specified date. So the
finder would accept the user's latitude, longitude and a date.

like test case = "User's Source Latitude: 40.7128, User's Source Longitude: -74.0060, Search Date:
2024-03-15" (url = http://localhost:8081/event/find?latitude=40.7128&longitude=-74.0060&date=2024-03-15)
it will return 44 events


Demonstration: googleDrive link : - https://drive.google.com/file/d/1esXABh5qHEPMP2RqK5PthRbvPWoFvLzq/view?usp=sharing
