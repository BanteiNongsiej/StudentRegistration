package com.example.StudentRegistration.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.StudentRegistration.Model.User;
import com.example.StudentRegistration.Repository.UserRepository;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String registerUser(User user){
		User existingUser=userRepository.findByEmail(user.getEmail());
		if(existingUser!=null) {
			return "Email"+user.getEmail()+" already in use";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.registerUser(user);
		return "User register successfully";
	}
	
	public User loginUser(String email,String password) {
		System.out.println("Attempting to find user with email: " + email);
		User user=userRepository.findByEmail(email);
		if(user!=null && passwordEncoder.matches(password,user.getPassword())) {
			return user;
		}
		return null;
	}

	public User getUserById(int id){
		return userRepository.getUserById(id);
	}

	public List<User> getAllUsers(){
		return userRepository.getAllUsers();
	}
}
