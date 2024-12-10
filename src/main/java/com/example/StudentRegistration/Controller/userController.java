package com.example.StudentRegistration.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.StudentRegistration.Model.User;
import com.example.StudentRegistration.Service.UserService;

@Controller
@RequestMapping("/auth")
public class userController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/auth/loginPage";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "registerPage";
    }

    @PostMapping("/registerPage")
    public ModelAndView register(@ModelAttribute User user) {
        System.out.println("Inside Register Controller");
        ModelAndView modelAndView = new ModelAndView();

        if (user.getEmail() == null || user.getPassword() == null) {
            modelAndView.setViewName("registerPage");
            modelAndView.addObject("error", "Email and password are required.");
            return modelAndView;
        }

        String response = userService.registerUser(user);
        if (response.equals("User registered successfully")) {
            modelAndView.setViewName("redirect:/auth/loginPage");
        } else {
            modelAndView.setViewName("registerPage");
            modelAndView.addObject("error", response);
        }

        return modelAndView;
    }

    @PostMapping("/loginPage")
    public ModelAndView login(@RequestParam String email, @RequestParam String password) {
        System.out.println("Inside Login Controller with email " + email);
        ModelAndView modelAndView = new ModelAndView();

        User user = userService.loginUser(email, password);
        if (user != null) {
            modelAndView.setViewName("StudentForm"); // Navigate to createStudent.html on successful login
        } else {
            modelAndView.setViewName("loginPage");
            modelAndView.addObject("error", "Wrong Credentials");
        }

        return modelAndView;
    }
}
