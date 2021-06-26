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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.model.Produttore;
import it.uniroma3.siw.spring.model.Vino;
import it.uniroma3.siw.spring.service.PiattoService;
import it.uniroma3.siw.spring.service.ProduttoreService;
import it.uniroma3.siw.spring.service.VinoService;
import it.uniroma3.siw.spring.validator.VinoValidator;

@Controller
public class VinoController {

	@Autowired
	private VinoService vinoService;

	@Autowired
	private VinoValidator vinoValidator;

	@Autowired
	private ProduttoreService produttoreService;

	@Autowired
	private PiattoService piattoService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Vino vinoTemp;



	Produttore pr = new Produttore("produttore alghero", "veramente bravo");
	//	
	//	Piatto p1 = new Piatto("pasta");
	//	Piatto p2 = new Piatto("carne");
	//	Piatto p3 = new Piatto("pesce");
	//	Piatto p4 = new Piatto("frutta");
	//	List<Piatto> piatti = Arrays.asList(p1,p2,p3,p4);
	//	
	//	produttoreService.inserisci(pr);
	//	
	//	Vino v = new Vino("Sa lughe","https://www.gros.it/photo/2020/03/11/e/scancube_001/photo/000200440_scancubefoto0001.jpg",7f);
	//	v.setProduttore(pr);
	//
	//	v.setPiatti(piatti);
	//	
	//	List<Vino> vini = Arrays.asList(v);
	//	
	//	vinoService.inserisci(v);
	//	for(Piatto piatto : piatti) {
	//		piatto.setVini(vini);
	//		this.piattoService.inserisci(piatto);
	//	}



	/*Popola la form*/
	@RequestMapping(value="/admin/addVino", method = RequestMethod.GET)
	public String addVino(Model model) {
		logger.debug("PASSO ALLA FORM addVino");
		model.addAttribute("vino", new Vino());
		return "/admin/vinoForm.html";
	}

	/*Si occupa di gestire la richiesta quando viene selezionato
	 * un vino dalla pagina dei vari vini*/
	@RequestMapping(value = "/vino/{id}", method = RequestMethod.GET)
	public String getVino(@PathVariable("id") Long id, Model model) {
		Vino vino = this.vinoService.vinoPerId(id);
		List<Piatto> piatti = vino.getPiatti();
		model.addAttribute("vino", vino);
		model.addAttribute("piatti", piatti);
		logger.debug("***PIATTI VINO:  "+vino.getPiatti().toString());
		return "vino.html";
	}

	/*Si occupa di gestire la richiesta quando viene selezionato
	 * il link della pagina vini*/
	@RequestMapping(value = "/vini", method = RequestMethod.GET)
	public String getVini(Model model) {
		model.addAttribute("vini", this.vinoService.tutti());
		return "vini.html";
	}
	
	@RequestMapping(value = "/viniAlfabetico", method = RequestMethod.GET)
	public String getViniAlfa(Model model) {
		model.addAttribute("vini", this.vinoService.tuttiOrdinatiAlfabetico());
		return "vini.html";
	}
	
	@RequestMapping(value = "/viniVotoCrescente", method = RequestMethod.GET)
	public String getViniNomeCresc(Model model) {
		model.addAttribute("vini", this.vinoService.tuttiOrdinatiPerVotoCres());
		return "vini.html";
	}
	
	@RequestMapping(value = "/viniVotoDecrescente", method = RequestMethod.GET)
	public String getViniNomeDecresc(Model model) {
		model.addAttribute("vini", this.vinoService.tuttiOrdinatiPerVotoDec());
		return "vini.html";
	}

	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/admin/inserisciVino", method = RequestMethod.POST)
	public String newProduttore(@ModelAttribute("vino") Vino vino, 
			Model model, BindingResult bindingResult) {
		this.vinoValidator.validate(vino, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("Non ci sono errori, passo alla conferma");
			this.vinoTemp = vino;
			return "admin/confermaVinoForm.html";
		}
		return "admin/vinoForm.html";
	}

	@RequestMapping(value = "/admin/confermaVino", method = RequestMethod.POST)
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
			return "/admin/vinoForm.html";
		}
	}


}