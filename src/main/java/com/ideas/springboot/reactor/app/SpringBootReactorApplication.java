package com.ideas.springboot.reactor.app;

import java.util.ArrayList;
import java.util.List;

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
		
		Flux<String> nombres = Flux.just("Israel Bejarano", "Juan Fulano", "José Fulano", "Rita Zutano", "Pedro Sillano", "Pedro Delgado");
		
		Flux<Usuario> usuarios = nombres.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
				.filter(usuario -> usuario.getNombre().equalsIgnoreCase("pedro"))
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
		
		usuarios.subscribe(e -> log.info(e.toString()), 
				error -> log.error(error.getMessage()), new Runnable() {
					
					@Override
					public void run() {
						log.info("Ha finalizado con éxito la ejecución del observable.");						
					}
				});
	}
}