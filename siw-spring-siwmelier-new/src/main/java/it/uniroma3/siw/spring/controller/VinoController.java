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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.model.Catalogo;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.model.Produttore;
import it.uniroma3.siw.spring.model.Vino;
import it.uniroma3.siw.spring.service.CatalogoService;
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
	private CatalogoService catalogoService;

	@Autowired
	private PiattoService piattoService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Vino vinoTemp;

	/*Si occupa di gestire la richiesta quando viene selezionato
	 * un vino dalla pagina dei vari vini*/
	@RequestMapping(value = "/vino/{id}", method = RequestMethod.GET)
	public String getVino(@PathVariable("id") Long id, Model model) {
		Vino vino = this.vinoService.vinoPerId(id);
		List<Piatto> piatti = vino.getPiatti();
		Produttore prod = vino.getProduttore();
		model.addAttribute("vino", vino);
		model.addAttribute("piatti", piatti);
		model.addAttribute("produttore", prod);
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

	/*Popola la form*/
	@RequestMapping(value="/admin/addVino", method = RequestMethod.GET)
	public String addVino(Model model) {
		logger.debug("PASSO ALLA FORM addVino");
		model.addAttribute("vino", new Vino());
		model.addAttribute("piatti", this.piattoService.tutti());
		model.addAttribute("produttori", this.produttoreService.tutti());
		model.addAttribute("cataloghi", this.catalogoService.tutti());
		return "/admin/vinoForm.html";
	}

	/*raccoglie e valida i dati della form*/
	@RequestMapping(value = "/admin/inserisciVino", method = RequestMethod.POST)
	public String newProduttore(@ModelAttribute("vino") Vino vino, 
			Model model, BindingResult bindingResult) {
		this.vinoValidator.validate(vino, bindingResult);
		if (!bindingResult.hasErrors()) {
			logger.debug("Non ci sono errori, passo alla conferma");
			this.vinoTemp = vino;
			model.addAttribute("vino",vino);
			model.addAttribute("produttore", vino.getProduttore());
			model.addAttribute("piatti", vino.getPiatti());
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
			vinoTemp.setNome(vinoTemp.getNome().toLowerCase());
			this.vinoService.inserisci(vinoTemp);

			//aggiungo il vino ai vini del produttore
			Produttore p = this.vinoTemp.getProduttore();
			List<Vino> vini = this.vinoService.viniPerProduttore(p);
			vini.add(vinoTemp);
			p.setViniProdotti(vini);
			this.produttoreService.inserisci(p);
			
			//aggiungo i piatti
			List<Piatto> piatti = this.vinoTemp.getPiatti();
			for(Piatto piatto : piatti) {
				List<Vino> viniPiatti = this.vinoService.viniPerPiatti(piatto);
				viniPiatti.add(vinoTemp);
				piatto.setVini(viniPiatti);
				this.piattoService.inserisci(piatto);
			}
			
			//aggiorno il catalogo
			Catalogo c = this.vinoTemp.getCatalogo();
			List<Vino> viniCat = this.vinoService.viniPerCatalogo(c);
			viniCat.add(vinoTemp);
			c.setVini(viniCat);
			catalogoService.inserisci(c);

			model.addAttribute("vini", this.vinoService.tutti());
			return "vini.html";
		}
		else {
			return "/admin/vinoForm.html";
		}
	}


}
