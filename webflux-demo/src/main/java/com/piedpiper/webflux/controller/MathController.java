package com.piedpiper.webflux.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piedpiper.webflux.dto.Response;
import com.piedpiper.webflux.service.MathService;

@RestController
@RequestMapping("math")
public class MathController {
	
	@Autowired
	MathService mathService;
	
	@GetMapping("square/{number}")
	public Response square(@PathVariable("number") int number) {
		return mathService.square(number);
	}
	
	@GetMapping("table/{number}")
	public List<Response> table(@PathVariable("number") int number) {
		return mathService.table(number);
	}
}
