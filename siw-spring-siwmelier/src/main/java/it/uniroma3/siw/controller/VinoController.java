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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Produttore;
import it.uniroma3.siw.model.Vino;
import it.uniroma3.siw.service.VinoService;
import it.uniroma3.siw.validator.VinoValidator;

@Controller
public class VinoController {
	
	@Autowired
	private VinoService vinoService;
	
	@Autowired
	private VinoValidator vinoValidator;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Vino vinoTemp;
	
	/*Popola la form*/
	@RequestMapping(value="/addVino", method = RequestMethod.GET)
	public String addVino(Model model) {
		logger.debug("PASSO ALLA FORM addVino");
		model.addAttribute("vino", new Vino());
		return "VinoForm.html";
	}

	/*Si occupa di gestire la richiesta quando viene selezionato
	 * un vino dalla pagina dei vari vini*/
	@RequestMapping(value = "/vino/{id}", method = RequestMethod.GET)
	public String getVino(@PathVariable("id") Long id, Model model) {
		Vino vino = this.vinoService.vinoPerId(id);
		model.addAttribute("vino", vino);

		/*popola la lista dei piatti di questo produttore corrente*/
		//PIATTI DEL VINO
		return "vino.html";
	}
	
	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina vini*/
	@RequestMapping(value = "/vini", method = RequestMethod.GET)
	public String getVini(Model model) {
		model.addAttribute("vini", this.vinoService.tutti());
		return "vini.html";
	}
	
	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/inserisciVino", method = RequestMethod.POST)
	public String newProduttore(@ModelAttribute("vino") Vino vino, 
			Model model, BindingResult bindingResult) {
		//this.vinoValidator.validate(vino, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("Non ci sono errori, passo alla conferma");
			this.vinoTemp = vino;
			return "confermaVinoForm.html";
		}
		return "VinoForm.html";
	}

	@RequestMapping(value = "/confermaVino", method = RequestMethod.POST)
	public String confermaVino(Model model,
			@RequestParam(value = "action") String comando) {
		model.addAttribute("vino",vinoTemp);

		if(comando.equals("confirm")) {
			/*cambio le stringhe con caratteri tutti minuscoli per facilitare la ricerca*/
			vinoTemp.setNome(vinoTemp.getNome().toLowerCase());					

			logger.debug("CONFERMO e SALVO dati produttore");
			this.vinoService.inserisci(vinoTemp);
			model.addAttribute("vini", this.vinoService.tutti());
			return "vini.html";
		}
		else {
			return "vinoForm.html";
		}
	}

	
}
