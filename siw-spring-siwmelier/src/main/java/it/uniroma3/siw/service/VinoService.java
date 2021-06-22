package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Catalogo;
import it.uniroma3.siw.model.Colore;
import it.uniroma3.siw.model.Corposita;
import it.uniroma3.siw.model.Effervescenza;
import it.uniroma3.siw.model.Produttore;
import it.uniroma3.siw.model.Vino;
import it.uniroma3.siw.repository.VinoRepository;

@Service
public class VinoService {
	
	@Autowired
	private VinoRepository vinoRepository;

	@Transactional
	public Vino inserisci(Vino vino) {
		return this.vinoRepository.save(vino);
	}
	
	@Transactional
	public Vino vinoPerId(Long id) {
		Optional<Vino> optional = this.vinoRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			return null;
		}
	}
	
	@Transactional
	public boolean alreadyExists(Vino vino) {
		List<Vino> lista = this.vinoRepository.findByNome(vino.getNome());
		if(lista.size()>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transactional
	public List<Vino> tutti(){
		return this.vinoRepository.findAll();
	}
	
	@Transactional
	public List<Vino> viniPerNome(String nome){
		return this.vinoRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Vino> viniPerAnno(Float anno){
		return this.vinoRepository.findByAnnoImbottigliamento(anno);
	}
	
	@Transactional
	public List<Vino> viniPerVoto(Float voto){
		return this.vinoRepository.findByVoto(voto);
	}
	
	@Transactional
	public List<Vino> viniPerGradazione(Float gradazione){
		return this.vinoRepository.findByGradazione(gradazione);
	}
	
	@Transactional
	public List<Vino> viniPerPrezzo(Float prezzo){
		return this.vinoRepository.findByPrezzo(prezzo);
		}
	
	@Transactional
	public List<Vino> viniPerEffervescenza(Effervescenza effervescenza){
		return this.vinoRepository.findByEffervescenza(effervescenza);
	}
	
	@Transactional
	public List<Vino> viniPerCorposita(Corposita corposita){
		return this.vinoRepository.findByCorposita(corposita);
		}
	
	@Transactional
	public List<Vino> viniPerColore(Colore colore){
		return this.vinoRepository.findByColore(colore);
	}
	
	@Transactional
	public List<Vino> viniPerProduttore(Produttore produttore){
		return (List<Vino>) this.vinoRepository.findByProduttore(produttore);
	}
	
	@Transactional
	public List<Vino> viniPerCatalogo(Catalogo catalogo){
		return (List<Vino>) this.vinoRepository.findByCatalogo(catalogo);
	}
}
