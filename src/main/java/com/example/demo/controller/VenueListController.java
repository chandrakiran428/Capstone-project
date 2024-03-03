package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Event;
import com.example.demo.entity.VenueList;
import com.example.demo.service.BookingService;
import com.example.demo.service.VenueListService;

@Controller
public class VenueListController {
	
	@Autowired
	private VenueListService venuelistservice;
	
	@Autowired
    private BookingService bookingService;

	// Handler method to handle students
	@GetMapping("/VenueList")
	public  String venuelist(Model model) {
		model.addAttribute("Venues", venuelistservice.getAllVenueList());  
		return "VenueList";
		//return venuelistservice.getAllVenueList();		
	}
	
	
	@GetMapping("/addvenue")
	public String addvenue(Model model) {
		VenueList venues = new VenueList();
		model.addAttribute("venue", venues);
		return "addvenue";
	}
	
	
	@PostMapping("/addvenue")
	public String saveVenue(@ModelAttribute("venues") VenueList venues) {
		venuelistservice.saveVenue(venues);
	    return "redirect:/index";
	}
  
	@GetMapping("/newbooking")
	public String newbooking(Model model) {
		List<Event> events = bookingService.getNewBookings();
        model.addAttribute("bookings", events);
		return "newBooking";
	}
	
    
    @GetMapping("/history")
    public String getBookingHistory(Model model) {
    	List<Event> events = bookingService.getBookingHistory();
        model.addAttribute("bookings", events);
		return "bookingHistory";
    }
    
  /*  @GetMapping("/newbooking/newView/{id}")
    public String viewBookingDetails(@PathVariable Long id, Model model) {
       // Assuming you have a method in your service to retrieve booking details by ID
        Optional<Event> booking = bookingService.getBookingById(id);
        
        // Add the booking details to the model
        model.addAttribute("booking", booking);  
    	
    	
        
        // Return the view name for newView.html
        return "newView";
    } */
    @GetMapping("/newbooking/newView/{id}")
    public String viewBookingDetails(@PathVariable Long id, Model model) {
       // Assuming you have a method in your service to retrieve booking details by ID
        Optional<Event> booking = bookingService.getBookingById(id);
        
        // Add the booking details to the model
        model.addAttribute("booking", booking);  
        
        
        // Return the view name for newView.html
        return "newView";
    }


}
