package com.groupal.universia.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {
	
	@RequestMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
  
    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
   
    @RequestMapping(value="/login")
    public String login(){
        return "login";
    }
    
    @RequestMapping(value="/logout")
    public String logout(){
        return "login";
    }
   
    @RequestMapping(value="/403")
    public String Error403(){
        return "403";
    }
 
}
