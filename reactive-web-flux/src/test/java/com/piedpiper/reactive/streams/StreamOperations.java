package com.piedpiper.reactive.streams;

public class StreamOperations {

	// print all numbers in the intNumberStream

	public static void printAllIntNumbers() {
		StreamSources.intNumberStream().forEach(i -> System.out.println(i));
	}

	public static void printAllIntNumbersLTFive() {
		StreamSources.intNumberStream().filter(i -> i < 5).forEach(i -> System.out.println(i));
	}

	// Print second and third numbers greater than five
	public static void printAllIntNumbersSecondAndThirdNumbersGTFive() {
		StreamSources.intNumberStream().filter(i -> i > 5).skip(1).limit(2).forEach(i -> System.out.println(i));
	}

	public static void printFirstIntNumbersGTFiveElseNegOne() {
		int first = StreamSources
		.intNumberStream()
		.filter(i -> i > 30)
		.findFirst()
		.orElse(-1)
		.intValue();
		
		System.out.println(first);
	}
	
	public static void matchStreamsIntStreamToUser() {
		StreamSources.userStream()
		.filter(user -> 
		StreamSources.intNumberStream().anyMatch(i -> i == user.getId()))
		.forEach(user -> System.out.println(user));
	    			
	}
	
	public static void matchStreamsIntStreamToUserWithFlatMap() {
		StreamSources.intNumberStream()
		.flatMap(i -> StreamSources.userStream().filter(user -> i == user.getId()))
		.map(user -> user.getFirstName())
		.forEach(user -> System.out.println(user));
		
	}
	
	

	public static void main(String[] args) {
		System.out.println("--------------Printing all intstream ----------------------------");
		printAllIntNumbers();

		System.out.println("--------------Printing all intstream Less than five----------------------------");
		printAllIntNumbersLTFive();

		System.out.println(
				"--------------Printing all intstream Second and third numbers greater than 5---------------------------");
		printAllIntNumbersSecondAndThirdNumbersGTFive();
		
		System.out.println(
				"--------------Printing all intstream first numbers greater than 5 else -1---------------------------");
		printFirstIntNumbersGTFiveElseNegOne();
		
		System.out.println(
				"--------------Printing all users matching int stream---------------------------");
		
		matchStreamsIntStreamToUser();
		
		System.out.println(
				"--------------Printing all users matching int stream using flat map---------------------------");
		
		matchStreamsIntStreamToUserWithFlatMap();
	}

}
