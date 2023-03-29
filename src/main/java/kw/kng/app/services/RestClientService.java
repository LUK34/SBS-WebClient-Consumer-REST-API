package kw.kng.app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import kw.kng.app.request.Passenger;
import kw.kng.app.response.Ticket;

@Service
public class RestClientService 
{
	@Value("${irctc.endpoint.book.ticket}")
	private String IRCTC_ENDPOINT_1;

	@Value("${irctc.endpoint.get.ticket}")
	private String IRCTC_ENDPOINT_2;
	
	
	public Ticket getTicketInfo(String ticketId)
	{
		WebClient webClient = WebClient.create(); // get WeClient instance
		
		 Ticket ticket = webClient.get() 								// represents HTTP GET request
								  .uri(IRCTC_ENDPOINT_2, ticketId) 		// ENDPOINT URL
								  .accept(MediaType.APPLICATION_JSON)	//accepts as JSON
								  .retrieve() 							// take response from response body
								  .bodyToMono(Ticket.class) 			// bind response body data to java object
								  .block(); 							// make sync call
		 if(ticket!=null) { 
			 return ticket;
		 }

		return null;
	}
	public Ticket processTicketBooking(Passenger passy)
	{
		WebClient webClient = WebClient.create(); // get WeClient instance
		 
		 Ticket ticket = webClient.post()
		 		  				  .uri(IRCTC_ENDPOINT_1)
		 		  				  .body(BodyInserters.fromValue(passy))
		 		  				  .header("Content-Type","application/json")
		 		  				  .accept(MediaType.APPLICATION_JSON)
		 		  				  .retrieve()
		 		  				  .bodyToMono(Ticket.class)
		 		  				  .block();
		 if(ticket!=null) { 
			 return ticket;
		 }
		return null;
	}
}

/*
 Please refer application.properties where we define the URL
 ----------------------------------------- processTicketBooking ----------------------------------------------------------------
 This Java code defines a RestClientService class that provides a method to book a ticket using an external API. Here's a breakdown of the code:

1.@Service is an annotation that marks this class as a Spring service,
 which means it will be a candidate for Spring's dependency injection mechanism, and can be used in other Spring components.

2.The RestClientService class is defined with a private constant IRCTC_ENDPOINT, 
which is a URL string representing the endpoint of an external ticket booking API.
This API likely belongs to the Indian Railway Catering and Tourism Corporation (IRCTC), given the name of the constant.

3. The processTicketBooking method accepts a Passenger object as an argument and is responsible for booking a ticket for the given passenger.

4. A RestTemplate object is created, which is a Spring class that provides a convenient way to make HTTP requests to RESTful APIs.

5. The postForEntity method of the RestTemplate class is called, passing the IRCTC_ENDPOINT as the target URL, the Passenger object as the request body,
 and the expected response type, Ticket.class. 
 The postForEntity method sends an HTTP POST request to the specified URL with the provided request body
 and returns a ResponseEntity object containing the response data.

6. The status code of the HTTP response is obtained from the ResponseEntity object using the getStatusCodeValue method
and stored in the statusCode variable.

7. An if statement checks whether the statusCode is equal to 200, which indicates a successful HTTP response. 
If the status code is 200, the Ticket object is extracted from the response using the getBody method of the ResponseEntity and returned as the result of the processTicketBooking method.

8. If the status code is not 200, the method returns null, indicating that the ticket booking was unsuccessful.

In summary, this code defines a RestClientService class that uses Spring's RestTemplate 
to send a ticket booking request to an external API for a given passenger and returns the booked ticket 
if successful or null if not.

----------------------------------------- getTicketInfo ----------------------------------------------------------------
 
 9.
 The service takes a ticketId parameter, which is the identifier of the ticket to retrieve. 
 It then creates a new instance of RestTemplate and uses it to make a GET request to the remote server. 
 The URL for the request is constructed using the IRCTC_ENDPOINT_2 constant, 
 which is set to the value of the irctc.endpoint.get.ticket property in the application.properties file. 
 The ticketId parameter is passed as a path variable in the URL.

10.
The response from the server is wrapped in a ResponseEntity object, which contains metadata about the response,
 such as the HTTP status code. The code checks if the status code is equal to 200 (OK), 
 indicating that the request was successful. If the status code is 200,
  the response body is extracted using the getBody() method and returned as a Ticket object.
   If the status code is not 200, the method returns null.

 */