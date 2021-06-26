package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Produttore;
import it.uniroma3.siw.spring.model.Regione;
import it.uniroma3.siw.spring.repository.RegioneRepository;

@Service
public class RegioneService {

	@Autowired
	private RegioneRepository regioneRepository;

	@Transactional
	public Regione inserisci(Regione regione) {
		return this.regioneRepository.save(regione);
	}

	@Transactional
	public Regione regionePerId(Long id) {
		Optional<Regione> optional = this.regioneRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			return null;
		}
	}

	@Transactional
	public boolean alreadyExists(Regione regione) {
		List<Regione> lista = this.regioneRepository.findByNome(regione.getNome());
		if(lista.size()>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transactional
	public List<Regione> tutti(){
		return this.regioneRepository.findAll();
	}
	
	@Transactional
	public List<Regione> tutteAlfabetico(){
		return this.regioneRepository.findByOrderByNomeAsc();
	}

	@Transactional
	public List<Regione> regioniPerNome(String nome){
		return this.regioneRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Regione> regioniPerProduttori(Produttore produttore){
		return this.regioneRepository.findByProduttori(produttore);
	}
}
