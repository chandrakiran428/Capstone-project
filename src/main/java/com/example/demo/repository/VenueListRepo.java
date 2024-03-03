package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VenueList;

public interface VenueListRepo  extends JpaRepository<VenueList, Long> {
	

}
