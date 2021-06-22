package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Produttore;
import it.uniroma3.siw.model.Regione;
import it.uniroma3.siw.repository.ProduttoreRepository;

@Service
public class ProduttoreService {
	
	@Autowired
	private ProduttoreRepository produttoreRepository;

	@Transactional
	public Produttore inserisci(Produttore produttore) {
		return this.produttoreRepository.save(produttore);
	}
	
	@Transactional
	public Produttore produttorePerId(Long id) {
		Optional<Produttore> optional = this.produttoreRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			return null;
		}
	}
	
	@Transactional
	public boolean alreadyExists(Produttore produttore) {
		List<Produttore> lista = this.produttoreRepository.findByNome(produttore.getNome());
		if(lista.size()>0) {
			return true;				
		}
		else {
			return false;
		}
	}
	
	@Transactional
	public List<Produttore> tutti(){
		return this.produttoreRepository.findAll();
	}
	
	@Transactional
	public List<Produttore> produttoriPerNome(String nome){
		return this.produttoreRepository.findByNome(nome);
	}

}

