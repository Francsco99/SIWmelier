package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Catalogo;
import it.uniroma3.siw.spring.model.Colore;
import it.uniroma3.siw.spring.model.Corposita;
import it.uniroma3.siw.spring.model.Effervescenza;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.model.Produttore;
import it.uniroma3.siw.spring.model.Vino;

public interface VinoRepository extends CrudRepository<Vino,Long> {

	public List<Vino> findByNome(String nome);
	
	public List<Vino> findByAnnoImbottigliamento(Float anno);
	
	public List<Vino> findByVoto(Float voto);
	
	public List<Vino> findByGradazione(Float gradi);
	
	public List<Vino> findByPrezzo(Float prezzo);
	
	public List<Vino> findByEffervescenza(Effervescenza effervescenza);
	
	public List<Vino> findByCorposita(Corposita corposita);
	
	public List<Vino> findByColore(Colore colore);
	
	public List<Vino> findByProduttore(Produttore produttore);
	
	//public List<Vino> findByPiatti(List<Piatto> piatti);
	
	public List<Vino> findByCatalogo(Catalogo catalogo);
	
	public List<Vino> findAll();
}
