package it.epicode.ecommerce.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TestController {
	
	@GetMapping("/test")
	public String test(){
		return "OK";
	}

}
