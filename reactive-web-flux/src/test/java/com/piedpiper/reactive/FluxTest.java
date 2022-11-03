package com.piedpiper.reactive;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FluxTest {

	@Test
	void testFluxJust() {
		var fruitFlux = Flux.just("Apple", "Orange", "Grapes", "Banana", "Strawberry");
		// fruitFlux.subscribe(fruit -> System.out.println(fruit));

		StepVerifier.create(fruitFlux).expectNext("Apple").expectNext("Orange").expectNext("Grapes")
				.expectNext("Banana").expectNext("Strawberry").expectComplete();
	}

	@Test
	public void testCreateFluxFromArray() {
		String[] array = { "Apple", "Orange", "Grapes", "Banana", "Strawberry" };

		var arrayFlux = Flux.fromArray(array);

		StepVerifier.create(arrayFlux).expectNext("Apple").expectNext("Orange").expectNext("Grapes")
				.expectNext("Banana").expectNext("Strawberry").expectComplete();
	}

	@Test
	public void testFluxFromIterator() {
		var list = List.of("Apple", "Orange", "Grapes", "Banana", "Strawberry");
		var iterFlux = Flux.fromIterable(list);

		StepVerifier.create(iterFlux).expectNext("Apple").expectNext("Orange").expectNext("Grapes").expectNext("Banana")
				.expectNext("Strawberry").expectComplete();

	}

	@Test
	public void testFluxRange() {

		var rangeFlux = Flux.range(10, 5);
		StepVerifier.create(rangeFlux).expectNext(10).expectNext(11).expectNext(12).expectNext(13).expectNext(14)
				.verifyComplete();

	}

	@Test
	public void testFluxInterval() {
		var flux = Flux.interval(Duration.ofSeconds(1)).take(5);

		StepVerifier.create(flux).expectNext(0L).expectNext(1L).expectNext(2L).expectNext(3L).expectNext(4L)
				.verifyComplete();
	}

	// Merging flux
	@Test
	public void testMergeFlux() {
		var charFlux = Flux.just("Garfield", "Kojak", "Barbossa").delayElements(Duration.ofMillis(500));

		var foodFlux = Flux.just("Lasagna", "Lolypop", "Apple").delaySubscription(Duration.ofMillis(250))
				.delayElements(Duration.ofMillis(500));

		var mergedFlux = Flux.merge(charFlux, foodFlux);

		StepVerifier.create(mergedFlux).expectNext("Garfield").expectNext("Lasagna").expectNext("Kojak")
				.expectNext("Lolypop").expectNext("Barbossa").expectNext("Apple").verifyComplete();

	}

	@Test
	public void testFluxZip() {
		var charFlux = Flux.just("Garfield", "Kojak", "Barbossa");
		var foodFlux = Flux.just("Lasagna", "Lolypop", "Apple");

		var zippedFlux = charFlux.zipWith(foodFlux);

		StepVerifier.create(zippedFlux)
				.expectNextMatches(p -> p.getT1().equals("Garfield") && p.getT2().equals("Lasagna"))
				.expectNextMatches(p -> p.getT1().equals("Kojak") && p.getT2().equals("Lolypop"))
				.expectNextMatches(p -> p.getT1().equals("Barbossa") && p.getT2().equals("Apple")).verifyComplete();

	}

	@Test
	public void testFluxZipWithCustomObject() {
		var charFlux = Flux.just("Garfield", "Kojak", "Barbossa");
		var foodFlux = Flux.just("Lasagna", "Lolypop", "Apple");

		var zippedFlux = Flux.zip(charFlux, foodFlux, (c, f) -> c + " eats " + f);
		StepVerifier.create(zippedFlux).expectNext("Garfield eats Lasagna").expectNext("Kojak eats Lolypop")
				.expectNext("Barbossa eats Apple").verifyComplete();
	}

	@Test
	public void testFirstWithSignalFlux() {
		Flux<String> slowFlux = Flux.just("sloath", "tortise", "snail").delaySubscription(Duration.ofMillis(500));

		Flux<String> fastFlux = Flux.just("chetaah", "hare", "squirrel");

		var fluxWithFirstSignal = Flux.firstWithSignal(slowFlux, fastFlux);

		StepVerifier.create(fluxWithFirstSignal).expectNext("chetaah").expectNext("hare").expectNext("squirrel")
				.verifyComplete();
	}

	// Filtering using skip, take, filter and distinct
	@Test
	public void testFluxskipWithNumber() {
		var list = List.of("Apple", "Orange", "Grapes", "Banana", "Strawberry");
		var iterFlux = Flux.fromIterable(list).skip(2);

		StepVerifier.create(iterFlux).expectNext("Grapes", "Banana", "Strawberry").verifyComplete();

	}

	@Test
	public void testFluxskipWithDuration() {
		var list = List.of("Apple", "Orange", "Grapes", "Banana", "Strawberry");
		var iterFlux = Flux.fromIterable(list).delayElements(Duration.ofSeconds(1)).skip(Duration.ofMillis(3500));

		StepVerifier.create(iterFlux).expectNext("Banana", "Strawberry").verifyComplete();

	}

	@Test
	public void testFluxTake() {
		var list = List.of("Apple", "Orange", "Grapes", "Banana", "Strawberry");
		var iterFlux = Flux.fromIterable(list).take(3);

		StepVerifier.create(iterFlux).expectNext("Apple", "Orange", "Grapes").verifyComplete();
	}

	@Test
	public void testFluxTakeDelay() {
		var list = List.of("Apple", "Orange", "Grapes", "Banana", "Strawberry");
		var iterFlux = Flux.fromIterable(list).delayElements(Duration.ofSeconds(1)).take(Duration.ofMillis(4500));

		StepVerifier.create(iterFlux).expectNext("Apple", "Orange", "Grapes", "Banana").verifyComplete();
	}

	@Test
	public void testFluxFilter() {
		var flux = Flux.just("Kudremukha", "Bhadra rivertern", "Kodachadri", "Kabini Jungle lodges", "Bandipura Tiger Reserve","Kemanngundi");
		
		var filteredFlux = flux.filter(e -> !e.contains(" "));
		
		StepVerifier.create(filteredFlux)
		.expectNext("Kudremukha", "Kodachadri", "Kemanngundi")
		.verifyComplete();
		
	}
	
	@Test
	public void testFluxDistinct() {
		var flux = Flux.just("tiger", "cat", "dog","lion","tiger","lion","cat")
				.distinct();
		
		StepVerifier.create(flux)
		.expectNext("tiger","cat","dog","lion")
		.verifyComplete();
	}
	
	//map and flatMap -----> Transformation
}
