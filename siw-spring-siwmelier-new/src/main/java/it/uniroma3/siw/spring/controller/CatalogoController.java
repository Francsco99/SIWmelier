package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.uniroma3.siw.spring.model.Catalogo;
import it.uniroma3.siw.spring.service.CatalogoService;
import it.uniroma3.siw.spring.service.VinoService;
import it.uniroma3.siw.spring.validator.CatalogoValidator;

@Controller
public class CatalogoController {
	
	@Autowired
	private CatalogoService catalogoService;
	
	@Autowired
	private VinoService vinoService;
	
	@Autowired
	private CatalogoValidator catalogoValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/*funzione temporanea per aggiungere i cataloghi*/
	@SuppressWarnings("unused")
	private void aggiungiCataloghi() {
		
		Catalogo c1 = new Catalogo("doc", "Denominazione di origine controllata");
		Catalogo c2 = new Catalogo("docg", "Denominazione di origine controllata e garantita");
		Catalogo c3 = new Catalogo("dop", "Denominazione di origine protetta");
		Catalogo c4 = new Catalogo("vdt", "Vino da tavola");
		
		List<Catalogo> cat = Arrays.asList(c1,c2,c3,c4);
		for(Catalogo c : cat) {
			this.catalogoService.inserisci(c);
		}
	}
	
	/*Popola la form*/
	@RequestMapping(value="/admin/addCatalogo", method = RequestMethod.GET)
	public String addCatalogo(Model model) {
		logger.debug("PASSO ALLA FORM addCatalogo");
		model.addAttribute("catalogo", new Catalogo());
		return "/admin/catalogoForm.html";
	}
	
	/*Si occupa di gestire la richiesta quando viene selezionato
	 * un catalogo dalla pagina dei vari cataloghi*/
	@RequestMapping(value = "/catalogo/{id}", method = RequestMethod.GET)
	public String getCatalogo(@PathVariable("id") Long id, Model model) {
		Catalogo catalogo = this.catalogoService.catalogoPerId(id);
		model.addAttribute("catalogo", catalogo);

		/*popola la lista dei vini di questo catalogo corrente*/
		model.addAttribute("vini",this.vinoService.viniPerCatalogo(catalogo));
		return "catalogo.html";
	}
	
	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina cataloghi*/
	@RequestMapping(value = "/cataloghi", method = RequestMethod.GET)
	public String getCataloghi(Model model) {
		
		//this.aggiungiCataloghi();
		model.addAttribute("cataloghi", this.catalogoService.tuttiCresc());
		return "cataloghi.html";
	}
	
	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/admin/inserisciCatalogo", method = RequestMethod.POST)
	public String newCatalogo(@ModelAttribute("catalogo") Catalogo catalogo, 
			Model model, BindingResult bindingResult) {
		this.catalogoValidator.validate(catalogo, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("Non ci sono errori, inserisco il catalogo nel db");
			this.catalogoService.inserisci(catalogo);
			return "cataloghi.html";
		}
		return "/admin/catalogoForm.html";
	} 
}
