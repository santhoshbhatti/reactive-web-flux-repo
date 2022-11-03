package com.piedpiper.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piedpiper.webflux.dto.Multiples;
import com.piedpiper.webflux.dto.Response;
import com.piedpiper.webflux.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {
	
	@Autowired
	private ReactiveMathService reactiveMathService;

	@GetMapping("square/{number}")
	public Mono<Response> square(@PathVariable("number") int number){
		return reactiveMathService.square(number);
	}
	
	@GetMapping("table/{number}")
	public Flux<Response> table(@PathVariable("number") int number){
		//return reactiveMathService.table(number);
		//Totally blocking.....The producer continues to process elements even if the subscriber/browser stops the request
		return reactiveMathService.tableDelay(number);
	}
	
	@GetMapping(value ="tableStream/{number}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Response> tableStream(@PathVariable("number") int number){
		//return reactiveMathService.table(number);
		//the processing does not stop immidiately after the browser stops the request because of blocking sleep----Non blocking
		return reactiveMathService.tableDelay(number);
	}
	
	@GetMapping(value ="tableNonBlockingStream/{number}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Response> tableNonBlockingStream(@PathVariable("number") int number){
		//return reactiveMathService.table(number);
		//the processing stops immidiately after the browser stops the request----Non blocking
		return reactiveMathService.tableDelayNonBlocking(number);
	}
	
	@GetMapping("tableNonStreamingNonBlocking/{number}")
	public Flux<Response> tableNonStreamingNonBlocking(@PathVariable("number") int number){
		//return reactiveMathService.table(number);
		//Totally blocking.....The producer continues to process elements even if the subscriber/browser stops the request
		return reactiveMathService.tableDelayNonBlocking(number);
	}
	
	@PostMapping("multiply")
	public Mono<Response> multiply(@RequestBody Mono<Multiples> multiples){
		return reactiveMathService.multiply(multiples);
	}
}
