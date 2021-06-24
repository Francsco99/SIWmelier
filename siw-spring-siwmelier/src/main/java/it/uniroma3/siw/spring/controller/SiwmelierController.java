package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

	private boolean viniAggiunti = false;

	@RequestMapping(value="/ricerca", method = RequestMethod.GET)
	public String getRicerca(Model model, 
			@RequestParam(value="cerca", required = true) String cerca) {

		String cercaLower = cerca.toLowerCase();

		logger.debug("Hai cercato" + cercaLower);

		return null;
	}


	/*funzione test per aggiungere vini*/
	private void aggiungiViniTest() {
		Vino v5 = new Vino("vino5","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg",10f);
		Vino v1 = new Vino("vino1","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg",9f);
		Vino v2 = new Vino("vino2","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg",8f);
		Vino v3 = new Vino("vino3","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg",7f);
		Vino v4 = new Vino("vino4","https://www.saporidoc.it/2036-thickbox_default/giro-di-sicilia-rossi.jpg",6f);
	}

	@RequestMapping(value= {"/", "index"}, method = RequestMethod.GET)
	public String getViniHome(Model model) {
		/*if(!viniAggiunti) {
			this.aggiungiViniTest();
		}*/
		model.addAttribute("viniDecr", this.vinoService.tuttiOrdinatiPerVotoDec());
		model.addAttribute("viniCresc", this.vinoService.tuttiOrdinatiPerVotoCres());
		return "index";
	}
}
