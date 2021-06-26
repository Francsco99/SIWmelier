package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.model.Vino;

public interface PiattoRepository extends CrudRepository<Piatto, Long> {

	public List<Piatto> findByNome(String nome);
	
	public List<Piatto> findAll();
	
	public List<Piatto> findByVini(Vino vini);
}
