package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Regione;

public interface RegioneRepository extends CrudRepository<Regione, Long> {
	
	public Regione findByNome(String nome);
	
	public List<Regione> findAll();

}
