package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.VenueList;
import com.example.demo.repository.VenueListRepo;



//public interface VenueListService {
//	public List<VenueList> getAllVenueList();
//
//	VenueList saveVenue(VenueList venues);
	
@Service
public class VenueListService {

	@Autowired
	private VenueListRepo venuelistrepo;
	
	
	

	public List<VenueList> getAllVenueList() {
		// TODO Auto-generated method stub
		return venuelistrepo.findAll();
	}




	public VenueList saveVenue(VenueList venues) {
		// TODO Auto-generated method stub
		return venuelistrepo.save(venues);
	}

}
