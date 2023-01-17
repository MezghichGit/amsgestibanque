package com.sip.ams.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sip.ams.entities.Provider;
import com.sip.ams.entities.Role;
import com.sip.ams.entities.User;
import com.sip.ams.repositories.ProviderRepository;
import com.sip.ams.repositories.UserRepository;
import com.sip.ams.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
@Controller
@RequestMapping("/agent/")

public class AgentController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private UserRepository userRepository;
    
    @GetMapping("list")
    //@ResponseBody
    public String listProviders(Model model) {
    	
    	List<User> lu = (List<User>)userRepository.findAll(); // list de tout les Users
    	List<User>la = new ArrayList<>();
    	
    	for(User user : lu)
    	{
    		 Set<Role>userRoles = user.getRoles();
    		 Object roles[] = userRoles.toArray();
  	         Role role = (Role)roles[0];
  	         String userRole = role.getRole();
  	         if(userRole.equals("AGENT"))
  	        	 la.add(user);
    		
    	}
   
    	
    	if(la.size()==0)
    		la=null;
    	model.addAttribute("nbr", la.size());
        model.addAttribute("agents", la);
    	
        return "agent/listAgents";
        
        //List<Provider> lp = (List<Provider>)providerRepository.findAll();
       // System.out.println(lp);
        
       // return "Nombre de fournisseurs = " + lp.size();
    }
    
    @GetMapping("add")
    public String AgentForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "agent/addAgent";
    }
    
    @PostMapping("add")
    public String addAgent(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "agent/addAgent";
        }
        userService.saveUser(user,"AGENT",1);
        return "redirect:list";
    }

    /*
    @GetMapping("delete/{id}")
    public String deleteProvider(@PathVariable("id") long id, Model model) {
    	
    	
    	//long id2 = 100L;
    	
        Provider provider = providerRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));
        
        System.out.println("suite du programme...");
        
        providerRepository.delete(provider);
        
  
        return "redirect:../list";
    }
    
    
    @GetMapping("edit/{id}")
    public String showProviderFormToUpdate(@PathVariable("id") long id, Model model) {
        Provider provider = providerRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + id));
        
        model.addAttribute("provider", provider);
        
        return "provider/updateProvider";
    }


    
    @PostMapping("update")
    public String updateProvider(@Valid Provider provider, BindingResult result, Model model) {
    	
    	
    	providerRepository.save(provider);
    	return"redirect:list";
    	
    }*/
}
