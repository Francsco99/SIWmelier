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
	

	@RequestMapping(value= {"/", "index"}, method = RequestMethod.GET)
	public String getViniIndex(Model model) {
		model.addAttribute("viniDecr", this.vinoService.tuttiOrdinatiPerVotoDec());
		model.addAttribute("viniCresc", this.vinoService.tuttiOrdinatiPerVotoCres());
		return "index";
	}
}
