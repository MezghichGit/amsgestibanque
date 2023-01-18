package com.sip.ams.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sip.ams.entities.Role;
import com.sip.ams.entities.User;
import com.sip.ams.repositories.UserRepository;

@Controller
@RequestMapping("/affectation/")
public class AffectationController {
	
	@Autowired
    private UserRepository userRepository;
	
    @GetMapping("encours")
    //@ResponseBody
    public String listProviders(Model model) {
    	
    	
		List<User> lu = (List<User>)userRepository.findAll(); // list de tout les Users
    	
		List<User>lc = new ArrayList<>();
    	
    	for(User user : lu)
    	{
    		 Set<Role>userRoles = user.getRoles();
    		 Object roles[] = userRoles.toArray();
  	         Role role = (Role)roles[0];
  	         String userRole = role.getRole();
  	         if(userRole.equals("CLIENT"))
  	        	 lc.add(user);
    		
    	}
   
    	
    	if(lc.size()==0)
    		lc=null;
    	model.addAttribute("nbr", lc.size());
        model.addAttribute("clients", lc);
    	
        return "affectation/encours";

    }

}
