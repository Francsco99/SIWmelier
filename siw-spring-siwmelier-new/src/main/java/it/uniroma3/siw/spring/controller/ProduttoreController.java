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

import it.uniroma3.siw.spring.model.Produttore;
import it.uniroma3.siw.spring.model.Regione;
import it.uniroma3.siw.spring.model.Vino;
import it.uniroma3.siw.spring.service.ProduttoreService;
import it.uniroma3.siw.spring.service.RegioneService;
import it.uniroma3.siw.spring.service.VinoService;
import it.uniroma3.siw.spring.validator.ProduttoreValidator;

@Controller
public class ProduttoreController {

	@Autowired
	private ProduttoreService produttoreService;

	@Autowired
	private VinoService vinoService;
	
	@Autowired
	private RegioneService regioneService;

	@Autowired
	private ProduttoreValidator produttoreValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*Si occupa di gestire la richiesta quando viene selezionato
	 * un produttore dalla pagina dei vari produttori*/
	@RequestMapping(value = "/produttore/{id}", method = RequestMethod.GET)
	public String getProduttore(@PathVariable("id") Long id, Model model) {
		Produttore produttore = this.produttoreService.produttorePerId(id);
		model.addAttribute("produttore", produttore);

		/*popola la lista dei vini di questo produttore corrente*/
		model.addAttribute("vini", this.vinoService.viniPerProduttore(produttore));
		return "produttore.html";
	}

	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina produttori*/
	@RequestMapping(value = "/produttori", method = RequestMethod.GET)
	public String getProduttori(Model model) {
		model.addAttribute("produttori", this.produttoreService.tutti());
		return "produttori.html";
	}
	
	/*Popola la form*/
	@RequestMapping(value="/admin/addProduttore", method = RequestMethod.GET)
	public String addProduttore(Model model) {
		logger.debug("PASSO ALLA FORM addProduttore");
		model.addAttribute("produttore", new Produttore());
		model.addAttribute("vini", vinoService.tuttiOrdinatiAlfabetico());
		model.addAttribute("regioni", this.regioneService.tutteAlfabetico());
		return "/admin/produttoreForm.html";
	}

	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/admin/inserisciProduttore", method = RequestMethod.POST)
	public String newProduttore(@ModelAttribute("produttore") Produttore produttore, 
			Model model, BindingResult bindingResult) {
		this.produttoreValidator.validate(produttore, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("Non ci sono errori, passo alla conferma");
			
			produttore.setNome(produttore.getNome().toLowerCase());
			this.produttoreService.inserisci(produttore);
			
			//devo settare la many to many, quindi prendo prima tutte
			//le regioni che ho selezionato dalla form
			List<Regione> regioni = produttore.getRegioni();
			
			//poi per ogni regioni prendo i produttori esistenti
			for(Regione r : regioni) {
				List<Produttore> produttori = this.produttoreService.produttoriPerRegione(r);
				
				//ci aggiungo quello corrente alla lista
				produttori.add(produttore);
				
				//faccio il set della lista aggiornata
				r.setProduttori(produttori);
				
				//il metodo save se già esiste un oggetto con quell'id
				//funziona come un UPDATE
				regioneService.inserisci(r);
			}
			
			//IDEA SIMILE A QUELLA DELLE REGIONI
			List<Vino> viniProd = produttore.getViniProdotti();
			for(Vino v : viniProd) {
				v.setProduttore(produttore);
				vinoService.inserisci(v);
			}
			
			model.addAttribute("viniDecr", this.vinoService.tuttiOrdinatiPerVotoDec());
			model.addAttribute("viniCresc", this.vinoService.tuttiOrdinatiPerVotoCres());
			return "/admin/homeAdmin.html";
		}
		return "/admin/produttoreForm.html";
	}
}
