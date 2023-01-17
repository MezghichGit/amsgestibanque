package com.sip.ams.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sip.ams.entities.Role;
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
            modelAndView.setViewName("/dashboard/registration");
        } else {
            userService.saveUser(user,"CLIENT",0);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/dashboard/registration");
        }
        return modelAndView;
    }


    /*@GetMapping("/403")
    public String error403() {
        return "/error/403";
    }*/

//sip2023amine
    //sipmezghich2023@gmail.com
	    @GetMapping("/")
	    public String accueil(Model model) {
	        return "dashboard/index";  
	    }
	    
	    @GetMapping("/dashboard")
	    public String dashbaord(Model model) {
	    	
	 
	    	 //1-Récuparation de la session du user Connecté <<Authentication>>
	    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	         //2-Récupéartion du User
	    	 User user = userService.findUserByEmail(auth.getName());
	         
	         //System.out.println(user.toString());
	         model.addAttribute("user", user);
	         //3-Récupération des roles du user
	         Set<Role> userRoles = user.getRoles();
	         //4-Conversion du set vers tableau pour la récupération du premier role
	         Object roles[] = userRoles.toArray();
	         //System.out.println(roles[0].toString()); // On suppose qu'on a un seul role par user
	         //5-Récupéation du rôle : userRole
	         Role role = (Role)roles[0];
	         String userRole = role.getRole();
	         //System.out.println(userRole);
	         
	         
	         switch(userRole) {
	         case "ADMIN" : return "dashboard/admin"; 
	         case "AGENT" : return "dashboard/agent";
	         case "CLIENT" : return "dashboard/client";
	         default : return "dashboard/index";
	         }
	        
	         
	       
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
