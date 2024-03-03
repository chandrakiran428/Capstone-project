package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.BookingService;

@Controller
@RequestMapping("/bookings")
public class BookingController {
 
	@Autowired
    private BookingService bookingService;
   
	 @Autowired
	  private  CustomerRepository CustomerRepository;
	
    @GetMapping
    public String getAllBookings(Model model) {
        List<Event> events = bookingService.getAllBookings();
        model.addAttribute("bookings", events);
        
        return "booking";
    }
   
	
}
