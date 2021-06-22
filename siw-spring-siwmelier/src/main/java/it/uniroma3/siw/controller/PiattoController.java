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
import it.uniroma3.siw.service.PiattoService;
import it.uniroma3.siw.validator.PiattoValidator;

@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private PiattoValidator piattoValidator;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*Popola la form*/
	@RequestMapping(value="/addPiatto", method = RequestMethod.GET)
	public String addPiatto(Model model) {
		logger.debug("PASSO ALLA FORM addPiatto");
		model.addAttribute("piatto", new Piatto());
		return "piattoForm.html";
	}
	
	/*Si occupa di gestire la richiesta quando viene selezionato
	 * un piatto dalla pagina dei vari piatti*/
	@RequestMapping(value = "/piatto/{id}", method = RequestMethod.GET)
	public String getPiatto(@PathVariable("id") Long id, Model model) {
		Piatto piatto = this.piattoService.piattoPerId(id);
		model.addAttribute("piatto", piatto);

		/*popola la lista dei vini di questo piatto corrente*/
		//GETVINI
		return "piatto.html";
	}
	
	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina piatti*/
	@RequestMapping(value = "/piatti", method = RequestMethod.GET)
	public String getPiatti(Model model) {
		model.addAttribute("piatti", this.piattoService.tutti());
		return "piatti.html";
	}
	
	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/inserisciPiatto", method = RequestMethod.POST)
	public String newPiatto(@ModelAttribute("piatto") Piatto piatto, 
			Model model, BindingResult bindingResult) {
		this.piattoValidator.validate(piatto, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("Non ci sono errori, inserisco il piatto nel db");
			this.piattoService.inserisci(piatto);
			return "piatti.html";
		}
		return "piattoForm.html";
	} 

}
