package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.model.Vino;
import it.uniroma3.siw.spring.service.VinoService;

@Controller
public class SiwmelierController {

	@Autowired
	private VinoService vinoService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());


	/*funzione di appoggio per la ricerca con una parola chiave*/
	private List<Vino> ricercaVini(String parolaChiave){
		String cercaLower = parolaChiave.toLowerCase();
		List<String> parole = Arrays.asList(cercaLower.split(" "));
		List<Vino> vini = new ArrayList<>();
		logger.debug("Hai cercato" + cercaLower);

		for(String parola : parole) {

			logger.debug("Sto cercando corrispondenze con: "+ parola);

			for(Vino v : this.vinoService.viniPerNome(parola)) {
				vini.add(v);
			}
		}
		return vini;
	}

	/*gestisce la richiesta da parte della vista per la ricerca
	 * con una parola chiave*/
	@RequestMapping(value="/ricerca", method = RequestMethod.GET)
	public String getRicerca(Model model, 
			@RequestParam(value="cerca", required = true) String cerca) {

		List<Vino> vini = this.ricercaVini(cerca);
		model.addAttribute("vini",vini);
		model.addAttribute("cerca", cerca);

		return "risultatoRicerca.html";
	}
	
	/*funzione test per aggiungere vini*/
	@SuppressWarnings("unused")
	private void aggiungiViniTest() {
		Vino v5 = new Vino("vino5","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg",10f);
		Vino v1 = new Vino("vino1","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg",9f);
		Vino v2 = new Vino("vino2","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg",8f);
		Vino v3 = new Vino("vino3","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg",7f);
		Vino v4 = new Vino("vino4","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg",6f);
		this.vinoService.inserisci(v1);
		this.vinoService.inserisci(v2);
		this.vinoService.inserisci(v3);
		this.vinoService.inserisci(v4);
		this.vinoService.inserisci(v5);
	}

	@RequestMapping(value= {"/", "index"}, method = RequestMethod.GET)
	public String getViniIndex(Model model) {
		//this.aggiungiViniTest();
		model.addAttribute("viniDecr", this.vinoService.tuttiOrdinatiPerVotoDec());
		model.addAttribute("viniCresc", this.vinoService.tuttiOrdinatiPerVotoCres());
		return "index";
	}
}
