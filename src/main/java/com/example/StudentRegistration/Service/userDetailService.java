package com.example.StudentRegistration.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.StudentRegistration.Model.User;
import com.example.StudentRegistration.Repository.UserRepository;

@Service
public class userDetailService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Custom method to load user by email
    public UserDetails loadUserByEmail(String email) {
        com.example.StudentRegistration.Model.User user = userRepository.findByEmail(email);
        
        if (user == null) {
            // Include the email in the exception message
            throw new UsernameNotFoundException("No user found with email: " + email);
        }

        // Return the UserDetails object
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER") // You can define roles here if needed
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user by username (email): " + username);  // Log the email
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

}
