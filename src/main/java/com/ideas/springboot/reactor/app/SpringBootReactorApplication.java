package com.ideas.springboot.reactor.app;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ideas.springboot.reactor.app.models.Usuario;

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
		
		Flux<Usuario> nombres = Flux.just("Israel", "Juan", "José", "Rita")
				.map(nombre -> new Usuario(nombre.toUpperCase(), null))
				.doOnNext(usuario -> {
					if(usuario == null) {
						throw new RuntimeException("nombres no puede ser vacío");
					}
					System.out.println(usuario.getNombre());
				})
				.map(usuario -> {
					String nombre = usuario.getNombre().toLowerCase();
					usuario.setNombre(nombre);
					return usuario;
				});
		
		nombres.subscribe(e -> log.info(e.getNombre()), 
				error -> log.error(error.getMessage()), new Runnable() {
					
					@Override
					public void run() {
						log.info("Ha finalizado con éxito la ejecución del observable.");						
					}
				});
	}
}