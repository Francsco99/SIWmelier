package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Produttore;
import it.uniroma3.siw.model.Regione;

public interface ProduttoreRepository extends CrudRepository<Produttore, Long>{

	public Produttore findByNome(String nome);
	
	public List<Produttore> findAll();
	
	public List<Produttore> findByRegioni(List<Regione> regioni);
}
