package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.EventRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/eventForm")
    public String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event_form";
    }

    @PostMapping("/submit")
    public String submitEvent(Event event) {
    	eventRepository.save(event);
        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    public String showConfirmation() {
        return "confirmation";
    }
    
    
/*
    @GetMapping("/editProfile/{id}")
    public String editProfile(Model model, @RequestParam("id") Long userId) {
        // Fetch the user from the database by ID
        Optional<User> userOptional = customerRepository.findById(userId);
        if (userOptional.isPresent()) {
            // Pass the user object to the view
            model.addAttribute("user", userOptional.get());
            return "editProfile";
        } else {
            // Handle case where user is not found
            return "error"; // Redirect to an error page or handle accordingly
        }
    }

    @PostMapping("/updateProfile/{id}")
    public String updateProfile(@RequestParam("id") Long userId,
                                @RequestParam("address") String address,
                                @RequestParam("mobile") String mobile) {
        // Fetch the user from the database by ID
        Optional<User> userOptional = customerRepository.findById(userId);
        if (userOptional.isPresent()) {
            // Update the user's address and mobile
            User user = userOptional.get();
            user.setAddress(address);
            user.setMobile(mobile);
            // Save the updated user back to the database
            customerRepository.save(user);
            // Redirect to a success page or back to the profile page
            return "redirect:/profile";
        } else {
            // Handle case where user is not found
            return "error"; // Redirect to an error page or handle accordingly
        }
    }
    */
    
//    @GetMapping("/editProfile")
//    public String editProfile(Model model, @RequestParam("id") Long userId) {
//        Optional<User> userOptional = customerRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            model.addAttribute("user", userOptional.get());
//            return "editProfile";
//        } else {
//            return "error"; // Redirect to an error page or handle accordingly
//        }
//    }
//
//    @PostMapping("/updateProfile")
//    public String updateProfile(@RequestParam("id") Long userId,
//                                @RequestParam("username") String username,
//                                @RequestParam("mobile") String mobile) {
//        Optional<User> userOptional = customerRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            user.setUsername(username);
//            user.setMobile(mobile);
//            customerRepository.save(user);
//            return "redirect:/userDetails?userId=" + userId;
//        } else {
//            return "error"; // Redirect to an error page or handle accordingly
//        }
//    }
    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam("userId") Long userId,
                                @RequestParam("address") String address,
                                @RequestParam("mobile") String mobile) {
        Optional<User> userOptional = customerRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAddress(address);
            user.setMobile(mobile);
            customerRepository.save(user);
            return "redirect:/editProfile?userId=" + userId;
        } else {
            return "error";
        }
    } 
   
}


