package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import it.uniroma3.siw.model.Catalogo;
import it.uniroma3.siw.repository.CatalogoRepository;

@Service
public class CatalogoService {

	@Autowired
	private CatalogoRepository catalogoRepository;

	@Transactional
	public Catalogo inserisci(Catalogo c) {
		return catalogoRepository.save(c);
	}

	@Transactional
	public Catalogo catalogoPerId(Long id) {
		Optional<Catalogo> optional = catalogoRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			return null;
		}
	}

	@Transactional
	public boolean alreadyExists(Catalogo c) {
		List<Catalogo> lista = this.catalogoRepository.findByNome(c.getNome());
		if(lista.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transactional
	public List<Catalogo> cataloghiPerNome(String nome){
		return this.catalogoRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Catalogo> tutti(){
		return this.catalogoRepository.findAll();
	}
}
