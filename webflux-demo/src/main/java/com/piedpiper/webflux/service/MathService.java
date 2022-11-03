package com.piedpiper.webflux.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.piedpiper.webflux.dto.Response;
import com.piedpiper.webflux.utils.SleepUtil;
@Service
public class MathService {
	
	
	public Response square(int number) {
		var response  = new Response(number * number);
		response.setDate(new Date());
		return response;
	}

	public List<Response> table(int number) {
		
		List<Response> list =IntStream.rangeClosed(1, 10)
		.map(next -> next * number)
		.peek(i -> SleepUtil.sleepSeconds(1))
		.mapToObj((int num) ->{
			var response = new Response(num);
			response.setDate(new Date());
			return response;
		})
		.peek(res -> System.out.println(res))
		.collect(Collectors.toList());
		
		

		return list;
	}
}
