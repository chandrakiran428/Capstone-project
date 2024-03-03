package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Vendor;
import com.example.demo.repository.VendorRepository;
import com.example.demo.service.VendorService;

@RestController
public class VendorController {

	
	@Autowired
	private VendorService vendorservice;

	
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
    @GetMapping("/fetchVendors")
    public ResponseEntity<List<String>> fetchVendorNames() {
        List<String> vendorNames = vendorservice.getAllVendors().stream()
                                    .map(Vendor::getVendor_company)
                                    .collect(Collectors.toList());
        return new ResponseEntity<>(vendorNames, HttpStatus.OK);
    }
}
