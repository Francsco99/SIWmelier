package it.uniroma3.siw.spring.controller;

import java.util.Arrays;
import java.util.List;

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

import it.uniroma3.siw.spring.model.Produttore;
import it.uniroma3.siw.spring.model.Regione;
import it.uniroma3.siw.spring.service.ProduttoreService;
import it.uniroma3.siw.spring.service.RegioneService;
import it.uniroma3.siw.spring.validator.RegioneValidator;

@Controller
public class RegioneController {

	@Autowired
	private RegioneService regioneService;

	@SuppressWarnings("unused")
	@Autowired
	private ProduttoreService produttoreService;

	@Autowired
	private RegioneValidator regioneValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*funzione test per aggiungere regioni*/
	@SuppressWarnings("unused")
	private void aggiungiRegioniTest() {
		List<String> regioni = Arrays.asList("valle d'aosta","piemonte",
				"lombardia","trentino","veneto","friuli","liguria",
				"emilia","toscana","marche","umbria","lazio","abruzzo",
				"molise","campania","puglia","basilicata","calabria","sicilia",
				"sardegna");
		for(String r : regioni) {
			Regione reg = new Regione(r);
			regioneService.inserisci(reg);
		}
	}

	/*Popola la form*/
	@RequestMapping(value="/admin/addRegione", method = RequestMethod.GET)
	public String addRegione(Model model) {
		logger.debug("PASSO ALLA FORM addRegione");
		model.addAttribute("regione", new Regione());
		return "/admin/regioneForm.html";
	}

	/*Si occupa di gestire la richiesta quando viene selezionato
	 * una regione dalla pagina delle varie regioni*/

	@RequestMapping(value = "/regione/{id}", method = RequestMethod.GET)
	public String getRegione(@PathVariable("id") Long id, Model model) {
		Regione regione = this.regioneService.regionePerId(id);
		model.addAttribute("regione", regione);
		
		List<Produttore> produttori = this.produttoreService.produttoriPerRegione(regione);
		model.addAttribute("produttori", produttori);
		return "regione.html";
	}


	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina regioni*/
	@RequestMapping(value = "/regioni", method = RequestMethod.GET)
	public String getRegioni(Model model) {
		//this.aggiungiRegioniTest();
		model.addAttribute("regioni", this.regioneService.tutteAlfabetico());
		return "regioni.html";
	}

	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/admin/inserisciRegione", method = RequestMethod.POST)
	public String newRegione(@ModelAttribute("regione") Regione regione, 
			Model model, BindingResult bindingResult) {
		this.regioneValidator.validate(regione, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("Non ci sono errori, inserisco la regione nel db");
			this.regioneService.inserisci(regione);
			return "regioni.html";
		}
		return "/admin/regioneForm.html";
	} 

}
