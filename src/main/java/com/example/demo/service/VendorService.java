package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Vendor;
import com.example.demo.repository.VendorRepository;

@Service
public class VendorService {

	
	@Autowired
    private VendorRepository vendorRepository;

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getVendorById(int id) {
        return vendorRepository.findById(id).orElse(null);
    }

    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }
}
