package kw.kng.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kw.kng.app.request.Passenger;
import kw.kng.app.response.Ticket;
import kw.kng.app.services.RestClientService;

@Controller
public class RestClientController 
{
	@Autowired
	RestClientService rcs;
	
	@GetMapping("/")
	public String index(Model model) 
	{
		Passenger pa=new Passenger();
		model.addAttribute("passenger",pa);
		return "index";
	}

	@GetMapping("/search")
	public String searchPage()
	{
		return "search";
	}
	
	@GetMapping("/searchTicket")
	public String searchTicket(@RequestParam("ticketId") String ticketId,Model model) 
	{
		System.out.println("Ticket Id :: "+ticketId);
		Ticket ticketInfo=rcs.getTicketInfo(ticketId);
		model.addAttribute("ticket",ticketInfo);
		return "search";
	}

	@PostMapping("/bookTicket")
	public String bookTicket(@ModelAttribute("passenger")Passenger req, Model model)
	{
		Ticket ti= rcs.processTicketBooking(req);
		model.addAttribute("ticket",ti);
		return "success";
	}
}
