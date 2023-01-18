package com.sip.ams.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sip.ams.entities.Affectation;
import com.sip.ams.entities.Provider;
import com.sip.ams.entities.Role;
import com.sip.ams.entities.User;
import com.sip.ams.repositories.AffectationRepository;
import com.sip.ams.repositories.UserRepository;
import com.sip.ams.services.UserService;

@Controller
@RequestMapping("/affectation/")
public class AffectationController {
	
	@Autowired
	UserService userService;
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	AffectationRepository affectationRepository;
	
	
    @GetMapping("encours")
    //@ResponseBody
    public String listProviders(Model model) {
    	
    	
		
    	
    	List<User>lc = getUsersByRole("CLIENT");
    	
    	if(lc.size()==0)
    		lc=null;
    	List<User>la = getUsersByRole("AGENT");
    	
    	
    	model.addAttribute("nbr", lc.size());
        model.addAttribute("clients", lc);
        model.addAttribute("agents", la);
    	
        return "affectation/encours";

    }
    
    @PostMapping("save")
    public String saveAffectation(Model model, @RequestParam("idAgent")int idAgent, @RequestParam("idClient")int idClient) {
    	
    	Affectation affectation = new Affectation();
    	affectation.setIdAgent(idAgent);
    	affectation.setIdClient(idClient);
    	affectation.setDateAffectation(new Date());
    	
    	 //1-Récuparation de la session du user Connecté <<Authentication>>
   	 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //2-Récupéartion du User
   	 	User admin = userService.findUserByEmail(auth.getName());
   	 
   	 	affectation.setIdAdmin(admin.getId());
    	// setters pour affectation
   	    affectation.setStatus("En attente de validation");
       
        affectationRepository.save(affectation);
        
        // envoyer un email vers l'agent correspondant pour lui dire qu'il a une demande à valider
        return "redirect:encours";
    }
    
    @GetMapping("agent")
    //@ResponseBody
    public String listClientByAgent(Model model) {
    	
    	//1-Récuparation de la session du user Connecté <<Authentication>>
   	 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //2-Récupéartion du User
   	 	User agentEnCours = userService.findUserByEmail(auth.getName());
   	 
    	
		List<Affectation> laf = affectationRepository.findAffectationByAgent(agentEnCours.getId());
		List<User> usersAgent = new ArrayList<>();
		
		for(Affectation affectation : laf)
		{
			//System.out.println(affectation.getIdClient());
			
			Optional<User> user = userRepository.findById(affectation.getIdClient());
			if(user.isPresent())
			{
				usersAgent.add(user.get());
			}
			
		}
    	
		System.out.println(usersAgent);
    	
    	
    	if(usersAgent.size()==0)
    		usersAgent=null;
    	
    	
    	
    	model.addAttribute("nbr", usersAgent.size());
        model.addAttribute("clients", usersAgent);
        return "affectation/usersByAgent";

    }
    
    public List<User> getUsersByRole(String role)
    {
    	List<User> myList=new ArrayList<>();
    	
    	List<User> lu = (List<User>)userRepository.findAll(); // list de tout les Users
    	for(User user : lu)
    	{
    		 Set<Role>userRoles = user.getRoles();
    		 Object roles[] = userRoles.toArray();
  	         Role roleObj = (Role)roles[0];
  	         String userRole = roleObj.getRole();
  	         if(userRole.equals(role))
  	        	myList.add(user);
    		
    	}
    	
    	return myList;
    }

}
