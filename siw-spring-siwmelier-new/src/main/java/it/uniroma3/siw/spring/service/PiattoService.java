package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.model.Vino;
import it.uniroma3.siw.spring.repository.PiattoRepository;

@Service
public class PiattoService {
	
	@Autowired
	private PiattoRepository piattoRepository;

	@Transactional
	public Piatto inserisci(Piatto p) {
		return this.piattoRepository.save(p);
	}
	
	@Transactional
	public Piatto piattoPerId(Long id) {
		Optional<Piatto> optional = piattoRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			return null;
		}
	}
	
	@Transactional
	public boolean alreadyExists(Piatto p) {
		List<Piatto> lista = this.piattoRepository.findByNome(p.getNome());
		if(lista.size()>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transactional
	public List<Piatto> tutti(){
		return this.piattoRepository.findAll();
	}
	
	@Transactional
	public List<Piatto> piattiPerNome(String nome){
		return this.piattoRepository.findByNome(nome);
	}
	
	public List<Piatto> piattiPerVini(Vino vino){
		return this.piattoRepository.findByVini(vino);
	}
}
