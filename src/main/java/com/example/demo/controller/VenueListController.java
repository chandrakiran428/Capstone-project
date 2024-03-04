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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String viewBookingDetails(@PathVariable("id") Long id, Model model) {
       // Assuming you have a method in your service to retrieve booking details by ID
        Optional<Event> booking = bookingService.getBookingById(id);
        
        // Add the booking details to the model
        model.addAttribute("booking", booking);  
        
        
        // Return the view name for newView.html
        return "newView";
    }

    @GetMapping("/errorPage")
	public String errorPage(Model model) {
		
		return "confirmation";
	}
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam("bookingId") Long bookingId, @RequestParam("status") String status, RedirectAttributes redirectAttributes) {
        // Update status in the database
        boolean updateSuccess = bookingService.updateStatus(bookingId, status);
        
        if (updateSuccess) {
            // If the status is successfully updated, redirect the user to a success page
            redirectAttributes.addFlashAttribute("successMessage", "Booking status updated successfully!");
            return "redirect:/newBooking"; // Redirect to a page showing new bookings or any other appropriate page
        } else {
            // If the status update fails, redirect the user to an error page or show an error message
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update booking status.");
            return "redirect:/errorPage"; // Redirect to an error page or any other appropriate page
        }
    }
    
    
    @PostMapping("/newbooking/newView/{id}/accept")
    public String acceptEvent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean updateSuccess = bookingService.updateStatus(id, "accepted");

        if (updateSuccess) {
            redirectAttributes.addFlashAttribute("successMessage", "Event status updated to Accepted!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update event status.");
        }

        return "redirect:/newbooking/newView/{id}";
    }

    @PostMapping("/newbooking/newView/{id}/reject")
    public String rejectEvent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean updateSuccess = bookingService.updateStatus(id, "rejected");

        if (updateSuccess) {
            redirectAttributes.addFlashAttribute("successMessage", "Event status updated to Rejected!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update event status.");
        }

        return "redirect:/newbooking/newView/{id}";
    }

}
