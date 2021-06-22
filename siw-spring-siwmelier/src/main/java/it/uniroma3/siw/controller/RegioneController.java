package it.uniroma3.siw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.model.Regione;
import it.uniroma3.siw.service.ProduttoreService;
import it.uniroma3.siw.service.RegioneService;
import it.uniroma3.siw.validator.RegioneValidator;

@Controller
public class RegioneController {
	
	@Autowired
	private RegioneService regioneService;
	
	@Autowired
	private ProduttoreService produttoreService;
	
	@Autowired
	private RegioneValidator regioneValidator;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*Popola la form*/
	@RequestMapping(value="/addRegione", method = RequestMethod.GET)
	public String addRegione(Model model) {
		logger.debug("PASSO ALLA FORM addRegione");
		model.addAttribute("regione", new Regione());
		return "regioneForm.html";
	}
	
	/*Si occupa di gestire la richiesta quando viene selezionato
	 * una regione dalla pagina delle varie regioni*/
	@RequestMapping(value = "/regione/{id}", method = RequestMethod.GET)
	public String getRegione(@PathVariable("id") Long id, Model model) {
		Regione regione = this.regioneService.regionePerId(id);
		model.addAttribute("regione", regione);

		/*popola la lista dei produttori di questa regione corrente*/
		//model.addAttribute("produttori", this.produttoreService.produttoriPerRegione)
		return "regione.html";
	}
	
	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina regioni*/
	@RequestMapping(value = "/regioni", method = RequestMethod.GET)
	public String getRegioni(Model model) {
		model.addAttribute("regioni", this.regioneService.tutti());
		return "regioni.html";
	}

	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/inserisciRegione", method = RequestMethod.POST)
	public String newRegione(@ModelAttribute("regione") Regione regione, 
			Model model, BindingResult bindingResult) {
		this.regioneValidator.validate(regione, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("Non ci sono errori, inserisco la regione nel db");
			this.regioneService.inserisci(regione);
			return "regioni.html";
		}
		return "regioneForm.html";
	} 

}
