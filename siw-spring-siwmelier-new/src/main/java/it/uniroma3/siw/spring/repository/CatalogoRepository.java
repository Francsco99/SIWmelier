package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Catalogo;

public interface CatalogoRepository extends CrudRepository<Catalogo, Long> {
	
	public List<Catalogo> findByNome(String nome);
	
	public List<Catalogo> findAll();
	
	public List<Catalogo> findByOrderByNomeAsc();

}
