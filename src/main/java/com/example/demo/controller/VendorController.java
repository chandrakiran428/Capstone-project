package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Event;
import com.example.demo.entity.Vendor;
import com.example.demo.repository.VendorRepository;
import com.example.demo.service.BookingService;
import com.example.demo.service.VendorService;

@RestController
public class VendorController {

	
	@Autowired
	private VendorService vendorservice;
 @Autowired
 private BookingService bookingservice;
	
	@GetMapping("/vendors")
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorservice.getAllVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    @GetMapping("/vendor/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable("id") int id) {
        Vendor vendor = vendorservice.getVendorById(id);
        if (vendor != null) {
            return new ResponseEntity<>(vendor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
        Vendor createdVendor = vendorservice.createVendor(vendor);
        return new ResponseEntity<>(createdVendor, HttpStatus.CREATED);
    }
    
 /*   @GetMapping("/fetchVendors")
    public List<Vendor> fetchVendors() {
        return vendorservice.getAllVendors();
    }
*/
  /*  @GetMapping("/fetchVendors")
    public ResponseEntity<List<String>> fetchVendorNames() {
        List<String> vendorNames = vendorservice.getAllVendors().stream()
                                    .map(Vendor::getVendor_company)
                                    .collect(Collectors.toList());
        return new ResponseEntity<>(vendorNames, HttpStatus.OK);
    }
    */
    @GetMapping("/fetchVendorNames")
    public ResponseEntity<List<String>> fetchVendorNames() {
        List<Vendor> vendors = vendorservice.getAllVendors();
        List<String> vendorNames = vendors.stream()
                                    .map(Vendor::getVendor_company)
                                    .collect(Collectors.toList());
       
        return new ResponseEntity<>(vendorNames, HttpStatus.OK);
    }
   /* @PostMapping("/assignVendor/{bookingId}")
    public ResponseEntity<String> assignVendorToBooking(@PathVariable("bookingId") Long bookingId, @RequestParam("vendorId") Integer vendorId) {
        // Retrieve booking by ID
        Optional<Event> bookingOptional = bookingservice.getBookingById(bookingId);
        
        if (!bookingOptional.isPresent()) {
            // Booking not found, return 404 Not Found response
            return ResponseEntity.notFound().build();
        }

        Event booking = bookingOptional.get();

        // Retrieve vendor by ID
        Vendor vendor = vendorservice.getVendorById(vendorId);
        
        if (vendor == null) {
            // Vendor not found, return 404 Not Found response
            return ResponseEntity.notFound().build();
        }

        // Assign the vendor to the booking
        booking.setVendor(vendor);
        bookingservice.saveBooking(booking);

        // Return a success message
        return ResponseEntity.ok("Vendor successfully assigned to booking");
    } */
    @PostMapping("/assignVendor/{bookingId}")
    public ResponseEntity<String> assignVendorToBooking(@PathVariable("bookingId") Long bookingId, @RequestParam("vendorId") Integer vendorId) {
        boolean updateSuccess = bookingservice.assignVendorToBooking(bookingId, vendorId);

        if (updateSuccess) {
            return ResponseEntity.ok("Vendor successfully assigned to booking");
        } else {
            return ResponseEntity.badRequest().body("Failed to assign vendor to booking");
        }
    }

}
