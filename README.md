# FlightRoute
This repository contains a java program that finds the shortest connection travel time  
between airports, given a series of airport nodes and the time required to travel between them.
## Commands
`+ YYZ JFK 120 plane`  
(adds a connection from airport YYZ to airport JFK that takes 120 minutes using a plane)  

`- YYZ`  
(removes an airport from the database)  

`- YYZ JFK 120 plane`  
(removes a connection from airport YYZ to airport JFK that takes 120 minutes using plane)  

`? YYZ`  
(lists all connections from YYZ (lines in the format YYZ JFK 120 plane))  

`? YYZ LAX`  
(find the quickest route from YYZ to LAX; prints the total duration and then lists the individual connections for this option (lines in the format YYZ JFK 120 plane))  

`?`  
(list all connections in memory (lines in the format YYZ JFK 120 plane))  

`QUIT`   
(end the program)  
