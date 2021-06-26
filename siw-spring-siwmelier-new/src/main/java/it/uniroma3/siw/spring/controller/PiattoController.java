package it.uniroma3.siw.spring.controller;

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

import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.model.Vino;
import it.uniroma3.siw.spring.service.PiattoService;
import it.uniroma3.siw.spring.service.VinoService;
import it.uniroma3.siw.spring.validator.PiattoValidator;

@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private VinoService vinoService;
	
	@Autowired
	private PiattoValidator piattoValidator;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/*Si occupa di gestire la richiesta quando viene selezionato
	 * un piatto dalla pagina dei vari piatti*/
	@RequestMapping(value = "/piatto/{id}", method = RequestMethod.GET)
	public String getPiatto(@PathVariable("id") Long id, Model model) {
		Piatto piatto = this.piattoService.piattoPerId(id);
		model.addAttribute("piatto", piatto);

		/*popola la lista dei vini di questo piatto corrente*/
		return "piatto.html";
	}
	
	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina piatti*/
	@RequestMapping(value = "/piatti", method = RequestMethod.GET)
	public String getPiatti(Model model) {
		model.addAttribute("piatti", this.piattoService.tutti());
		return "piatti.html";
	}
	
	/*Popola la form*/
	@RequestMapping(value="/admin/addPiatto", method = RequestMethod.GET)
	public String addPiatto(Model model) {
		logger.debug("PASSO ALLA FORM addPiatto");
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("vini", this.vinoService.tuttiOrdinatiAlfabetico());
		return "/admin/piattoForm.html";
	}
	
	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/admin/inserisciPiatto", method = RequestMethod.POST)
	public String newPiatto(@ModelAttribute("piatto") Piatto piatto, 
			Model model, BindingResult bindingResult) {
		this.piattoValidator.validate(piatto, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("Non ci sono errori, inserisco il piatto nel db");
			
			piatto.setNome(piatto.getNome().toLowerCase());
			this.piattoService.inserisci(piatto);
			
			List<Vino> vini = piatto.getVini();
			
			for(Vino v : vini) {
				List<Piatto> piatti = this.piattoService.piattiPerVini(v);
				piatti.add(piatto);
				v.setPiatti(piatti);
				vinoService.inserisci(v);
			}
			model.addAttribute("viniDecr", this.vinoService.tuttiOrdinatiPerVotoDec());
			model.addAttribute("viniCresc", this.vinoService.tuttiOrdinatiPerVotoCres());
			return "/admin/homeAdmin.html";
		}
		return "/admin/piattoForm.html";
	} 

}
