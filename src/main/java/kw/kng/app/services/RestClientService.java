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
 -> WebClient is a predefined interface introduced in Spring 5.x version

-> Using WebClient interface we can develop REST Client logics

-> WebClient supports both sync & async communication.


Sync  : Blocking Thread (After sending request we have to wait for response)

Async : Non Blocking Thread (After sending request we no need to wait for response)



RestTemplate (C) : spring-boot-starter-web 

WebClient (I) : spring-boot-starter-webflux


 */
