package com.example.demo.controller;
import java.security.Principal;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.CustomerRepository;


@Controller
public class CustomerController {
	
	
        @Autowired
	    private  CustomerRepository CustomerRepository;
        
        // Hash the password before saving it to the database
        private String hashPassword(String password) {
            return BCrypt.hashpw(password, BCrypt.gensalt());
        }

        // Verify the password against its hashed version
        private boolean verifyPassword(String password, String hashedPassword) {
            return BCrypt.checkpw(password, hashedPassword);
        }


	    @GetMapping("/register")
	    public String showRegistrationForm(Model model) {
	        model.addAttribute("user", new User());
	        return "register";
	    }

	    @PostMapping("/register")
	    public String registerUser(@ModelAttribute("user") User user) {
	        // You may want to add validation and error handling here
	    	
	    	String hashedPassword = hashPassword(user.getPassword());
	        user.setPassword(hashedPassword);
	    	CustomerRepository.save(user);
	        return "redirect:/login";
	    	
	    }

	    @GetMapping("/login")
	    public String showLoginForm(Model model) {
	        model.addAttribute("user", new User());
	        return "login";
	    }
	    
	    @GetMapping("/events")
	    public String eventsHomePage(Model model) {
	      model.addAttribute("user", new User());
	        return "home";
	    }
	    
	    
//	    @PutMapping("/update")
//	    public User updateUser(@RequestBody User user) {
//	        return CustomerRepository.save(user);
//	    } 
//	    
//	    
	    

	    @PostMapping("/login")
	    public String loginUser(@ModelAttribute("user") User user) {
	        // You may want to add validation and error handling here
//	        User existingUser = CustomerRepository.findByUsername(user.getUsername());
//	        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
//	            // Successful login, redirect to home page or dashboard
//	        	//return "redirect:/eventForm";
//	        	return "redirect:/events";
//	        } else {
//	            // Failed login, redirect back to login page with error message
//	            return "redirect:/login";
//	        }
	    	
	    	
	    	User existingUser = CustomerRepository.findByUsername(user.getUsername());
	        if (existingUser != null && verifyPassword(user.getPassword(), existingUser.getPassword())) {
	            // Successful login, redirect to home page
	            return "redirect:/events";
	        } else {
	            // Failed login, redirect back to login page
	            return "redirect:/login";
	        }
	    }
	}

