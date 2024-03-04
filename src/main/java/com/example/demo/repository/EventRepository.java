package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Add custom query methods if needed
	List<Event> findByStatus(String status);
	 List<Event> findByStatusIn(List<String> statuses);
}
