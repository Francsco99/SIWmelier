package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.model.Vino;
import it.uniroma3.siw.spring.service.SiwmelierService;
import it.uniroma3.siw.spring.service.VinoService;
import it.uniroma3.siw.spring.validator.SiwmelierValidator;

@Controller
public class SiwmelierController {
	
	@Autowired
	private VinoService vinoService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@RequestMapping(value="/ricerca", method = RequestMethod.GET)
	public String getRicerca(Model model, 
			@RequestParam(value="cerca", required = true) String cerca) {
		
		String cercaLower = cerca.toLowerCase();
		
		logger.debug("Hai cercato" + cercaLower);
		
		return null;
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String getViniHome(Model model) {
		Vino v5 = new Vino("vino5","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg");
		Vino v1 = new Vino("vino1","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg");
		Vino v2 = new Vino("vino2","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg");
		Vino v3 = new Vino("vino3","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg");
		Vino v4 = new Vino("vino4","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg");
		
		
		vinoService.inserisci(v1);
		vinoService.inserisci(v2);
		vinoService.inserisci(v3);
		vinoService.inserisci(v4);
		vinoService.inserisci(v5);
		model.addAttribute("vini", this.vinoService.tutti());
		return "index.html";
	}
}
