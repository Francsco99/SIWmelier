package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Catalogo;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.model.Produttore;
import it.uniroma3.siw.spring.model.Vino;

public interface VinoRepository extends CrudRepository<Vino,Long> {

	public List<Vino> findByNome(String nome);
	
	public List<Vino> findByAnnoImbottigliamento(Float anno);
	
	public List<Vino> findByVoto(Float voto);
	
	public List<Vino> findByGradazione(Float gradi);
	
	public List<Vino> findByPrezzo(Float prezzo);
	
	public List<Vino> findByEffervescenza(Float effervescenza);
	
	public List<Vino> findByCorposita(Float corposita);
	
	public List<Vino> findByColore(String colore);
	
	public List<Vino> findByProduttore(Produttore produttore);
	
	public List<Vino> findByPiatti(Piatto piatto);
	
	public List<Vino> findByCatalogo(Catalogo catalogo);
	
	public List<Vino> findAll();
	
	public List<Vino> findByOrderByVotoDesc();
	
	public List<Vino> findByOrderByVotoAsc();
	
	public List<Vino> findByOrderByNomeAsc();
}
