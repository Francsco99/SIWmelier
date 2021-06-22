package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Catalogo;
import it.uniroma3.siw.model.Colore;
import it.uniroma3.siw.model.Corposita;
import it.uniroma3.siw.model.Effervescenza;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.model.Produttore;
import it.uniroma3.siw.model.Vino;

public interface VinoRepository extends CrudRepository<Vino,Long> {

	public Vino findByNome(String nome);
	
	public List<Vino> findByColore(Colore colore);
	
	public List<Vino> findByAnnoImbottigliamento(Float anno);
	
	public List<Vino> findByVoto(Float voto);
	
	public List<Vino> findByGradazione(Float gradi);
	
	public List<Vino> findByPrezzo(Float prezzo);
	
	public List<Vino> findByEffervescenza(Effervescenza effervescenza);
	
	public List<Vino> findByCorposita(Corposita corposita);
	
	public List<Vino> findByProduttore(Produttore produttore);
	
	public List<Vino> findByPiatto(Piatto piatto);
	
	public List<Vino> findByCatalogo(Catalogo catalogo);
}
