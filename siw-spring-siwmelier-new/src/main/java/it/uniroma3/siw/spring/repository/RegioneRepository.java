package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Produttore;
import it.uniroma3.siw.spring.model.Regione;

public interface RegioneRepository extends CrudRepository<Regione, Long> {
	
	public List<Regione> findByNome(String nome);
	
	public List<Regione> findAll();
	
	public List<Regione> findByOrderByNomeAsc();
	
	public List<Regione> findByProduttori(Produttore produttore);

}
