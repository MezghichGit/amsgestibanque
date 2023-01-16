package com.sip.ams.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sip.ams.entities.User;
import com.sip.ams.services.UserService;
@Controller
public class DashbordController {
	
	@Autowired
    private UserService userService;
	
    @GetMapping(value="/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard/login");
        return modelAndView;
    }
    
    @GetMapping(value="/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("dashboard/registration");
        return modelAndView;
    }
    
    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        
        User userExists = userService.findUserByEmail(user.getEmail());
        
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }


    /*@GetMapping("/403")
    public String error403() {
        return "/error/403";
    }*/


	    @GetMapping("/")
	    public String accueil(Model model) {
	        return "dashboard/index";  
	    }
	    
	    @GetMapping("/admin")
	    public String dashbaordAdmin(Model model) {
	        return "dashboard/admin";  
	    }
	    @GetMapping("/agent")
	    public String dashbaordAgent(Model model) {
	        return "dashboard/agent";  
	    }
	    @GetMapping("/client")
	    public String dashbaordAClient(Model model) {
	        return "dashboard/client";  
	    }
	

}
