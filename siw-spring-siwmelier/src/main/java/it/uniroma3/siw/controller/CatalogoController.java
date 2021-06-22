package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.uniroma3.siw.model.Catalogo;
import it.uniroma3.siw.model.Vino;
import it.uniroma3.siw.service.CatalogoService;
import it.uniroma3.siw.service.VinoService;
import it.uniroma3.siw.validator.CatalogoValidator;

@Controller
public class CatalogoController {
	
	@Autowired
	private CatalogoService catalogoService;
	
	@Autowired
	private VinoService vinoService;
	
	@Autowired
	private CatalogoValidator catalogoValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*Popola la form*/
	@RequestMapping(value="/addCatalogo", method = RequestMethod.GET)
	public String addCatalogo(Model model) {
		logger.debug("PASSO ALLA FORM addCatalogo");
		model.addAttribute("catalogo", new Catalogo());
		return "catalogoForm.html";
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
		model.addAttribute("cataloghi", this.catalogoService.tutti());
		return "cataloghi.html";
	}
	
	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/inserisciCatalogo", method = RequestMethod.POST)
	public String newCatalogo(@ModelAttribute("catalogo") Catalogo catalogo, 
			Model model, BindingResult bindingResult) {
		//this.catalogoValidator.validate(catalogo, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("Non ci sono errori, inserisco il catalogo nel db");
			this.catalogoService.inserisci(catalogo);
			return "cataloghi.html";
		}
		return "catalogoForm.html";
	} 
}
