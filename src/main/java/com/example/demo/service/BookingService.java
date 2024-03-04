package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.entity.Vendor;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.VendorRepository;

import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

	@Autowired
    private VendorRepository vendorrepository;
	
//    @Autowired
//    private BookingRepository bookingRepository;
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllBookings() {
        return eventRepository.findAll();
    }
    
    public List<Event> getNewBookings() {
       return eventRepository.findByStatus("pending");
    	//return eventRepository.findAll();
    }
    
    public List<Event> getBookingHistory() {
        List<String> statuses = Arrays.asList("accepted", "rejected");
        return eventRepository.findByStatusIn(statuses);
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

	 @Transactional
	    public boolean updateStatus(Long eventId, String status) {
	        Optional<Event> eventOptional = eventRepository.findById(eventId);
	        if (eventOptional.isPresent()) {
	            Event event = eventOptional.get();
	            event.setStatus(status);
	            eventRepository.save(event);
	            return true;
	        } else {
	            return false; // Event not found
	        }
	    }

	 @Transactional
	    public void saveBooking(Event booking) {
	        eventRepository.save(booking);
	    }

	 @Transactional
	    public boolean assignVendorToBooking(Long bookingId, Integer vendorId) {
	        Optional<Event> eventOptional = eventRepository.findById(bookingId);
	        Optional<Vendor> vendorOptional = vendorrepository.findById(vendorId);
	        if (eventOptional.isPresent() && vendorOptional.isPresent()) {
	            Event event = eventOptional.get();
	            Vendor vendor = vendorOptional.get();
	            event.setVendor(vendor);
	            eventRepository.save(event);
	            return true;
	        } else {
	            return false; // Event or Vendor not found
	        }
	    }
	
}

