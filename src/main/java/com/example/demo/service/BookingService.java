package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

//    @Autowired
//    private BookingRepository bookingRepository;
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllBookings() {
        return eventRepository.findAll();
    }
    
    public List<Event> getNewBookings() {
        return eventRepository.findByStatus("pending");
    }
    
    public List<Event> getBookingHistory() {
        return eventRepository.findByStatus("done");
    }
    
 // Assuming you have a service method to retrieve Event by ID
    public Event getEventById(Long eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            // If using lazy loading, initialize the vendor object
            // This fetches the vendor object from the database
            event.getVendor(); // This line will trigger the lazy loading
            return event;
        } else {
            return null; // Handle case where event is not found
        }
    }

    /* ------------------------------------------------------------------------ */
 /*   @Autowired
    private CustomerRepository customerRepository;

    public User findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return customerRepository.save(user);
    }
*/

	public Optional<Event> getBookingById(Long id) {
		// TODO Auto-generated method stub
		return eventRepository.findById(id);
	}
	
}

