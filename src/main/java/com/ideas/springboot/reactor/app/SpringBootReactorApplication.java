package com.ideas.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

/**
 * The Class SpringBootReactorApplication. Clase que inicializa la app. 
 * En este caso app de consola que genera un observable y nos subscribimos para ver React
 * @author Israel Bejarano
 */
@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	/**
	 * Run.
	 *
	 * @param args the args
	 * @throws Exception the exception
	 */
	@Override
	public void run(String... args) throws Exception {
		
		Flux<String> nombres = Flux.just("Israel", "Juan", "José", "Rita")
				.doOnNext(System.out::println); // es lo mismo que elemento -> System.out.println(elemento)
		
		nombres.subscribe(log::info);
	}
}