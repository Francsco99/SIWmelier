package it.uniroma3.siw.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.uniroma3.siw.spring.model.Vino;
import it.uniroma3.siw.spring.repository.VinoRepository;
import it.uniroma3.siw.spring.service.VinoService;


@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
