package com.piedpiper.webflux.service;

import java.time.Duration;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.piedpiper.webflux.dto.Multiples;
import com.piedpiper.webflux.dto.Response;
import com.piedpiper.webflux.utils.SleepUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ReactiveMathService {

	public Mono<Response> square(int number){
		
		return Mono.fromSupplier(()->{
			return new Response(new Date(),number * number);
		});
	}
	
	public Flux<Response> table(int number){
		var flux = Flux.range(1, 10)
				   .flatMap(num -> Mono.just(num)
				                   .map(i -> new Response(new Date(),i * number))
				                   .log()
				                   .subscribeOn(Schedulers.parallel()))
				   .log();
		return flux;
	}
	
	public Flux<Response> tableDelay(int number){
		var flux = Flux.range(1, 10)
				   .doOnNext(i -> SleepUtil.sleepSeconds(1))// this blocking sleep
				                                            //events like unsuubscribe wont reach and the process continues 
				   .log()
				   .map(i -> new Response(new Date(), i * number));
		return flux;
	}
	
	public Flux<Response> tableDelayNonBlocking(int number){
		var flux = Flux.range(1, 10)
				   .delayElements(Duration.ofSeconds(1)) 
				   .log()
				   .map(i -> new Response(new Date(), i * number));
		return flux;
	}

	public Mono<Response> multiply(Mono<Multiples> multiples) {
		
		return multiples
				.map(m -> m.getFirst() * m.getSecond())
				.map(res -> new Response(new Date(), res));
	}
}
