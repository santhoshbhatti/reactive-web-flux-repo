package com.piedpiper.webflux.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
@Data
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Response {
	
	private Date date;
	@NonNull
	private Integer result;
	

}
